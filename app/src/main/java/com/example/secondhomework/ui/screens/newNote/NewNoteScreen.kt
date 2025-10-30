package com.example.secondhomework.ui.screens.newNote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.secondhomework.R
import com.example.secondhomework.data.NoteRepositoryImpl
import com.example.secondhomework.ui.theme.LocalAppColorScheme

@Composable
fun NewNoteScreen(
    navController: NavController,
    viewModel: NewNoteViewModel = viewModel()
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val colors = LocalAppColorScheme.current

    Scaffold (containerColor = colors.backgroundColor){ innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .imePadding()
                .navigationBarsPadding()
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                ) {
                    if (viewModel.blankTitle != 0) {
                        Text(
                            text = stringResource(viewModel.blankTitle),
                            modifier = Modifier
                                .padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = viewModel.title,
                    onValueChange = { viewModel.onTitleChange(it) },
                    label = { Text(stringResource(R.string.title)) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.borderColor,
                        unfocusedBorderColor = colors.borderColor,
                        errorBorderColor = colors.errorMessageColor,
                        errorLabelColor = colors.errorMessageColor,
                        cursorColor = colors.borderColor,
                        focusedLabelColor = colors.textColor,
                        unfocusedLabelColor = colors.textColor,
                        focusedTextColor = colors.textColor,
                        unfocusedTextColor = colors.textColor,
                        focusedContainerColor = colors.textFieldColor,
                        unfocusedContainerColor = colors.textFieldColor
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.text,
                    onValueChange = { viewModel.onTextChange(it) },
                    label = { Text(stringResource(R.string.text)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.borderColor,
                        unfocusedBorderColor = colors.borderColor,
                        errorBorderColor = colors.errorMessageColor,
                        cursorColor = colors.borderColor,
                        focusedLabelColor = colors.textColor,
                        unfocusedLabelColor = colors.textColor,
                        focusedTextColor = colors.textColor,
                        unfocusedTextColor = colors.textColor,
                        focusedContainerColor = colors.textFieldColor,
                        unfocusedContainerColor = colors.textFieldColor
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            }

            Button(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    if (viewModel.titleFieldCorrect()) {
                        NoteRepositoryImpl.newNote(viewModel.title, viewModel.text)
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.buttonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .align(Alignment.BottomCenter)
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = stringResource(R.string.saveButton), color = colors.textColor)
            }
        }
    }

}