package com.example.androidcourse.ui.screens.thirdscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.androidcourse.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.sp


@Composable
fun ThirdScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: ThirdScreenViewModel = viewModel()
) {
    val colors = MaterialTheme.colorScheme

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = viewModel.currentText,
                    onValueChange = { viewModel.onTextChange(it) },
                    label = {
                        Text(
                            stringResource(R.string.reply_label),
                            color = colors.onBackground
                        )
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(color = colors.onBackground)
                )

                Button(
                    onClick = { viewModel.addMessage(viewModel.currentText) },
                    modifier = Modifier
                        .defaultMinSize(minHeight = 56.dp)
                        .align(Alignment.CenterVertically),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.primary,
                        contentColor = colors.onPrimary
                    )
                ) {
                    Text(stringResource(R.string.add_message))
                }

            }

            if (viewModel.textError != 0) {
                Text(text = stringResource(viewModel.textError), fontSize = 10.sp, color = colors.error
                )
            } else {
                Text(text = "", fontSize = 10.sp)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(viewModel.messages) { msg ->
                    Text(
                        text = msg,
                        color = colors.onBackground,
                        modifier = Modifier.wrapContentWidth()
                    )
                }
            }
        }
    }
}
