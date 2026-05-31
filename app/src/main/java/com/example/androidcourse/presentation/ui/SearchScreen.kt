package com.example.androidcourse.presentation.ui

import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.androidcourse.R
import com.example.androidcourse.domain.model.Game
import com.example.androidcourse.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigateToDetails: (Game) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var query by remember { mutableStateOf("") }

    val chartSectors by viewModel.chartSectors.collectAsState()
    var showChartDialog by remember { mutableStateOf(false) }
    var chartError by remember { mutableStateOf<String?>(null) }

    val onSearchClick = remember(query) {
        { viewModel.searchGames(query) }
    }

    if (showChartDialog) {
        PieChartInputDialog(
            onDismiss = { showChartDialog = false },
            onConfirm = { sectors ->
                try {
                    viewModel.setChartSectors(sectors)
                    chartError = null
                    showChartDialog = false
                } catch (e: IllegalArgumentException) {
                    chartError = e.message
                }
            },
            error = chartError,
            onErrorChange = { chartError = it }
        )
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text(stringResource(R.string.game_name_label)) },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onSearchClick,
                    enabled = query.isNotBlank()
                ) {
                    Text(stringResource(R.string.search_button))
                }

                Button(onClick = { showChartDialog = true }) {
                    Text("📊")
                }
            }

            if (chartSectors.isNotEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    PieChartView(sectors = chartSectors, chartSize = 280.dp)
                }
            }

            when (val s = state) {
                SearchViewModel.State.Empty -> {
                    Text(
                        stringResource(R.string.enter_game_name),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                SearchViewModel.State.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
                }

                is SearchViewModel.State.Success -> {
                    LazyColumn(Modifier.fillMaxSize()) {
                        item {
                            Snackbar {
                                Text(stringResource(R.string.loaded_from, s.source))
                            }
                        }

                        items(items = s.games, key = { game -> game.id }) { game ->
                            GameItem(
                                game = game,
                                onClick = { onNavigateToDetails(game) }
                            )
                        }
                    }
                }

                is SearchViewModel.State.Error -> {
                    val text = s.message ?: stringResource(s.resId ?: R.string.unknown_error)
                    Text(
                        text,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun GameItem(game: Game, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        AsyncImage(
            model = game.backgroundImage,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(end = 12.dp)
        )
        Text(game.name, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun PieChartInputDialog(
    onDismiss: () -> Unit,
    onConfirm: (List<Pair<Int, Int>>) -> Unit,
    error: String?,
    onErrorChange: (String?) -> Unit,
) {
    var input by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Круговая диаграмма") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Введите сектора в формате:\nключ:процент, ключ:процент\n\nПример: 1:25, 2:42, 3:33",
                    style = MaterialTheme.typography.bodySmall
                )
                OutlinedTextField(
                    value = input,
                    onValueChange = {
                        input = it
                        onErrorChange(null)
                    },
                    label = { Text("Сектора") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
                    isError = error != null,
                    supportingText = error?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val parsed = parseSectors(input)
                if (parsed == null) { onErrorChange("Неверный формат. Используйте: 1:25, 2:42, 3:33") }
                else { onConfirm(parsed) }
            }) { Text("Показать") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Отмена") } }
    )
}

private fun parseSectors(input: String): List<Pair<Int, Int>>? {
    return try {
        input.trim().split(",").map { token ->
                val parts = token.trim().split(":")
                require(parts.size == 2)
                parts[0].trim().toInt() to parts[1].trim().toInt()
            }
            .also { require(it.isNotEmpty()) }
    } catch (e: Exception) {
        null
    }
}