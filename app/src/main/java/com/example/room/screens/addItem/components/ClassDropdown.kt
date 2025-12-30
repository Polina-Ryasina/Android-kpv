package com.example.room.screens.addItem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.room.model.ClassOption
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.room.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDropdown(
    selectedClass: ClassOption?,
    onClassSelected: (ClassOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val classes = ClassOption.values().toList()
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedClass?.displayName ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.dropdown_class_label)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            classes.forEach { cls ->
                DropdownMenuItem(
                    text = { Text(cls.displayName) },
                    onClick = {
                        onClassSelected(cls)
                        expanded = false
                    }
                )
            }
        }
    }
}