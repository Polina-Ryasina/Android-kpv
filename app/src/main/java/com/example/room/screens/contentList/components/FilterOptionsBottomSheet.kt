package com.example.room.screens.contentList.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.room.model.ClassOption
import com.example.room.model.RaceOption
import com.example.room.R

@Composable
fun FilterOptionsBottomSheet(
    currentClass: ClassOption?,
    currentRace: RaceOption?,
    onClassSelected: (ClassOption?) -> Unit,
    onRaceSelected: (RaceOption?) -> Unit,
    onDismiss: () -> Unit
) {
    var step by remember { mutableStateOf(0) }
    var type by remember { mutableStateOf<FilterType?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(stringResource(R.string.filters), fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        when (step) {
            0 -> {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(modifier = Modifier.weight(1f), onClick = {
                        type = FilterType.CLASS
                        step = 1
                    }) {
                        Text(stringResource(R.string.dropdown_class_label), fontSize = 20.sp)
                    }
                    TextButton(modifier = Modifier.weight(1f), onClick = {
                        type = FilterType.RACE
                        step = 1
                    }) {
                        Text(stringResource(R.string.dropdown_race_label), fontSize = 20.sp)
                    }
                }
            }
            1 -> {
                val options = when (type) {
                    FilterType.CLASS -> ClassOption.values().toList()
                    FilterType.RACE -> RaceOption.values().toList()
                    else -> emptyList()
                }

                val currentSelection = when (type) {
                    FilterType.CLASS -> currentClass
                    FilterType.RACE -> currentRace
                    else -> null
                }

                val rows = options.chunked(2)
                rows.forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        row.forEach { option ->
                            val isSelected = option == currentSelection
                            TextButton(
                                onClick = {
                                    when (type) {
                                        FilterType.CLASS -> onClassSelected(option as ClassOption)
                                        FilterType.RACE -> onRaceSelected(option as RaceOption)
                                        else -> {}
                                    }
                                    onDismiss()
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                val name = when (type) {
                                    FilterType.CLASS -> (option as ClassOption).displayName
                                    FilterType.RACE -> (option as RaceOption).displayName
                                    else -> ""
                                }
                                Text(
                                    text = name,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 18.sp
                                )
                            }
                        }
                        if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = { step = 0 }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.back), fontSize = 18.sp)
                }
            }
        }
    }
}

private enum class FilterType { CLASS, RACE }