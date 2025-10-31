package com.example.androidcourse.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.androidcourse.R
import com.example.androidcourse.data.Note
import com.example.androidcourse.data.NoteRepositoryImpl
import com.example.androidcourse.nav.NavKeys
import com.example.androidcourse.ui.theme.EnumOfThemes
import com.example.androidcourse.ui.theme.LocalAppColorScheme


@Composable
fun MainScreen(
    onThemeChange: (EnumOfThemes) -> Unit,
    navController: NavController,
    email: String,
    viewModel: MainViewModel = viewModel()
) {
    val notes = viewModel.allNotes
    var dropdownExpanded by remember { mutableStateOf(false) }
    val dropdownOptions = EnumOfThemes.entries
    val density = LocalDensity.current
    val colors = LocalAppColorScheme.current


    Scaffold(containerColor = colors.backgroundColor) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                var buttonWidth by remember { mutableStateOf(0.dp) }
                OutlinedButton(
                    shape = RoundedCornerShape(16.dp),
                    onClick = { dropdownExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colors.buttonColor,
                        contentColor = colors.textColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            with(density) {
                                buttonWidth = coordinates.size.width.toDp()
                            }
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.dropdown))
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    }
                }


                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    modifier = Modifier
                        .width(buttonWidth)
                        .background(color = colors.textFieldColor)
                ) {
                    dropdownOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.displayName, color = colors.textColor) },
                            onClick = {
                                dropdownExpanded = false
                                onThemeChange(option)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = email,
                color = colors.textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                textDecoration = TextDecoration.Underline,
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (notes.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(text = stringResource(R.string.zeroNotes), color = colors.textColor)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(notes) { note: Note ->
                        Card(
                            border = BorderStroke(1.dp, colors.borderColor),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clickable {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        NavKeys.ID, note.id
                                    )
                                    navController.navigate(NavKeys.UPDATENOTE)
                                },
                            colors = CardDefaults.cardColors(containerColor = colors.textFieldColor)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = note.title,
                                    color = colors.textColor,
                                    fontWeight = FontWeight.Bold
                                )
                                if (note.text.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = note.text,
                                        color = colors.textColor
                                    )
                                }
                                Box (
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    IconButton(
                                        onClick = {
                                            NoteRepositoryImpl.deleteNote(note.id)
                                        },
                                        modifier = Modifier.align(Alignment.BottomEnd)
                                    ) { Icon(
                                        imageVector = Icons.Default.DeleteOutline,
                                        contentDescription = null,
                                        tint = colors.buttonColor
                                    ) }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate(NavKeys.NEWNOTE) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.buttonColor)
            ) {
                Text(text = stringResource(R.string.thirdScreen), color = colors.textColor)
            }
        }
    }
}
