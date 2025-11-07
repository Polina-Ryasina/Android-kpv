package com.example.androidcourse.ui.screens.secondscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.androidcourse.R

@Composable
fun SecondScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: SecondScreenViewModel = viewModel()
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    Scaffold(containerColor = colors.background) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(18.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(R.string.edit_notification),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = viewModel.notificationId,
                onValueChange = { viewModel.onNotificationIdChange(it) },
                label = { Text(stringResource(R.string.id), color = colors.onBackground) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = colors.onBackground)
            )

            viewModel.notificationIdError?.let { error ->
                Text(
                    text = error,
                    fontSize = 12.sp,
                    color = colors.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.newBody,
                onValueChange = { viewModel.onNewBodyChange(it) },
                label = { Text(stringResource(R.string.body_label), color = colors.onBackground) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(color = colors.onBackground)
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.updateNotification(context) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Text(stringResource(R.string.update_notification))
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.clearAllNotifications(context) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Text(stringResource(R.string.clear_notifications))
            }
        }
    }
}
