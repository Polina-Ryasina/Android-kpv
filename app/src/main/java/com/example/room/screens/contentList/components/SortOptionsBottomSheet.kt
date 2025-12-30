package com.example.room.screens.contentList.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.room.R

@Composable
fun SortOptionsBottomSheet(
    currentSort: SortOption?,
    onSortSelected: (SortOption?) -> Unit,
    onDismiss: () -> Unit
) {

    val options = SortOption.values().toList()
    val rows = options.chunked(2)

    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = stringResource(R.string.sort), fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { option ->
                    val isSelected = option == currentSort
                    TextButton(
                        onClick = {
                            val newSort = if (isSelected) null else option
                            onSortSelected(newSort)
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = if (isSelected) stringResource(R.string.without_sort) else option.displayName,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 20.sp
                        )
                    }
                }

                if (row.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
