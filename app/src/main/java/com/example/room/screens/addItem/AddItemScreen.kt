package com.example.room.screens.addItem

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.room.R
import com.example.room.screens.addItem.components.ClassDropdown
import com.example.room.screens.addItem.components.NumbersInput
import com.example.room.screens.addItem.components.RaceDropdown

@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel,
    onAddSuccess: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    if (viewModel.addSuccess) {
        LaunchedEffect(Unit) {
            onAddSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text(stringResource(R.string.add_item_name_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        Spacer(modifier = Modifier.height(8.dp))

        RaceDropdown(
            selectedRace = viewModel.race,
            onRaceSelected = { viewModel.onRaceChange(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        ClassDropdown(
            selectedClass = viewModel.characterClass,
            onClassSelected = { viewModel.onClassChange(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.add_item_level_label), fontSize = 20.sp)

            NumbersInput(
                level = viewModel.level,
                onLevelChange = { viewModel.onLevelChange(it) },
                max = 5
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.add_item_strength_label), fontSize = 20.sp)

            NumbersInput(
                level = viewModel.strength,
                onLevelChange = { viewModel.onStrengthChange(it) },
                max = 10
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.add_item_agility_label), fontSize = 20.sp)

            NumbersInput(
                level = viewModel.agility,
                onLevelChange = { viewModel.onAgilityChange(it) },
                max = 10
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.add_item_intelligence_label), fontSize = 20.sp)

            NumbersInput(
                level = viewModel.intelligence,
                onLevelChange = { viewModel.onIntelligenceChange(it) },
                max = 10
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.description,
            onValueChange = { viewModel.onDescriptionChange(it) },
            label = { Text(stringResource(R.string.add_item_description_label)) },
            singleLine = false,
            modifier = Modifier.fillMaxWidth().height(100.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                viewModel.addCharacter()
            })
        )

        Spacer(modifier = Modifier.height(16.dp))

        viewModel.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.addCharacter() },
            enabled = !viewModel.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text(stringResource(R.string.add_button_label))
            }
        }
    }
}