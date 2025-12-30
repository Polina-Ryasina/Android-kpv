package com.example.room.screens.contentList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.room.screens.contentList.components.CharacterCard
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.room.R
import com.example.room.screens.contentList.components.FilterOptionsBottomSheet
import com.example.room.screens.contentList.components.SortOptionsBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentListScreen(
    viewModel: ContentListViewModel,
    onAddItem: () -> Unit,
    onProfileClick: () -> Unit
) {
    viewModel.loadCharacters(null)

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSortSheet by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }



    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.characters), fontSize = 30.sp, fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    actions = {

                        IconButton(
                            onClick = {
                                if (viewModel.currentClassFilter != null || viewModel.currentRaceFilter != null) {
                                    viewModel.onClassFilterChange(null)
                                    viewModel.onRaceFilterChange(null)
                                } else {
                                    showFilterSheet = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (viewModel.currentClassFilter != null || viewModel.currentRaceFilter != null)
                                    Icons.Default.FilterAltOff else Icons.Default.FilterAlt,
                                contentDescription = stringResource(R.string.filters))
                        }

                        IconButton(onClick = { showSortSheet = true }) {
                            Icon(Icons.Default.Sort, contentDescription = stringResource(R.string.sort))
                        }

                        IconButton(onClick = onProfileClick) {
                            Icon(Icons.Default.Person, contentDescription = stringResource(R.string.profile))
                        }
                    }
                )
            },

            floatingActionButton = {
                FloatingActionButton(onClick = onAddItem) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_button_label))
                }
            }
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                when {
                    viewModel.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    viewModel.characters.isEmpty() -> {
                        Text(text = stringResource(R.string.no_characters), fontSize = 20.sp, modifier = Modifier.align(Alignment.Center))
                    }
                    else -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(viewModel.characters) { character -> CharacterCard(character) }
                        }
                    }
                }
            }
        }


        if (showSortSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSortSheet = false },
                sheetState = sheetState
            ) {
                SortOptionsBottomSheet(
                    currentSort = viewModel.currentSort,
                    onSortSelected = { sortOption ->
                        viewModel.onSortOptionChange(sortOption)
                        showSortSheet = false
                    },
                    onDismiss = { showSortSheet = false }
                )
            }
        }

        if (showFilterSheet) {
            ModalBottomSheet(
                onDismissRequest = { showFilterSheet = false },
                sheetState = sheetState
            ) {
                FilterOptionsBottomSheet(
                    currentClass = viewModel.currentClassFilter,
                    currentRace = viewModel.currentRaceFilter,
                    onClassSelected = {
                        viewModel.onClassFilterChange(it)
                        showFilterSheet = false
                    },
                    onRaceSelected = {
                        viewModel.onRaceFilterChange(it)
                        showFilterSheet = false
                    },
                    onDismiss = { showFilterSheet = false }
                )
            }
        }
    }

}
