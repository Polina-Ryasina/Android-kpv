package com.example.androidcourse.ui.screens.auth

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidcourse.R
import com.example.androidcourse.nav.NavKeys
import androidx.compose.material3.*
import com.example.androidcourse.ui.theme.LocalAppColorScheme


@Composable
fun AuthorizationScreen(
    navController: NavController,
    viewModel: AuthorizationViewModel = viewModel()

) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val colors = LocalAppColorScheme.current

    Scaffold (containerColor = colors.backgroundColor){ innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .imePadding()
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(100.dp))

                val errorToShow = when {
                    viewModel.emailInvalid != 0 -> viewModel.emailInvalid
                    viewModel.passwordInvalid != 0 -> viewModel.passwordInvalid
                    else -> null
                }

                errorToShow?.let { errorRes ->
                    Text(
                        text = stringResource(id = errorRes),
                        color = colors.errorMessageColor,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = { Text(text = stringResource(R.string.email)) },
                    isError = viewModel.emailInvalid != 0,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
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
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text(text = stringResource(R.string.password)) },
                    isError = viewModel.passwordInvalid != 0,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.borderColor,
                        unfocusedBorderColor = colors.borderColor,
                        errorLabelColor = colors.errorMessageColor,
                        errorBorderColor = colors.errorMessageColor,
                        cursorColor = colors.borderColor,
                        focusedLabelColor = colors.textColor,
                        unfocusedLabelColor = colors.textColor,
                        focusedTextColor = colors.textColor,
                        unfocusedTextColor = colors.textColor,
                        focusedContainerColor = colors.textFieldColor,
                        unfocusedContainerColor = colors.textFieldColor,
                        errorTrailingIconColor =  colors.errorMessageColor
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (viewModel.passwordIsVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (viewModel.passwordIsVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff
                        val description =
                            if (viewModel.passwordIsVisible) stringResource(R.string.visibilityOff)
                            else stringResource(R.string.visibilityOn)
                        IconButton(onClick = { viewModel.onTogglePasswordVisibility() }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    },
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(80.dp))
            }

            Button(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    val isValidate = viewModel.allFieldsCorrect()
                    if (isValidate) {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            NavKeys.EMAIL, viewModel.email
                        )
                        navController.navigate(NavKeys.MAIN)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.buttonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = stringResource(R.string.login), color = colors.textColor)
            }
        }
    }
}
