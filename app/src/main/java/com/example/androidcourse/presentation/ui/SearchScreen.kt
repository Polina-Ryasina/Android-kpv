package com.example.androidcourse.presentation.ui

import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

    val onSearchClick = remember(query) {
        { viewModel.searchGames(query) }
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