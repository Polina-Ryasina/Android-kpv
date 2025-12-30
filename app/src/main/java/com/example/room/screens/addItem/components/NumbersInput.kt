package com.example.room.screens.addItem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.room.R

@Composable
fun NumbersInput(
    level: String,
    onLevelChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    min: Int = 1,
    max: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Button(
            onClick = {
                val current = level.toIntOrNull() ?: min
                if (current > min) onLevelChange((current - 1).toString())
            },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(36.dp)
        ) {
            Text(stringResource(R.string.number_input_decrease_button))
        }

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = level,
            onValueChange = {
                val value = it.toIntOrNull()
                if (value != null && value in min..max) onLevelChange(it)
            },
            readOnly = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.width(80.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                val current = level.toIntOrNull() ?: min
                if (current < max) onLevelChange((current + 1).toString())
            },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(36.dp)
        ) {
            Text(stringResource(R.string.number_input_increase_button))
        }
    }
}