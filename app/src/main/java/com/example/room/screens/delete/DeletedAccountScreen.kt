package com.example.room.screens.delete

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.room.R
import com.example.room.data.UserRepository
import com.example.room.db.entity.UserEntity
import kotlinx.coroutines.launch

@Composable
fun DeletedAccountScreen(
    userId: Int,
    repository: UserRepository,
    onRestore: () -> Unit,
    onDelete: () -> Unit
) {

    var user by remember { mutableStateOf<UserEntity?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(userId) {
        user = repository.getUserById(userId)
    }

    user?.let { u ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(R.string.account_deleted_title),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                stringResource(R.string.account_deleted_message),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        repository.restoreUser(u.id)
                        onRestore()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(R.string.restore_account), fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        repository.deleteUser(u.id)
                        onDelete()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.delete_permanently), fontSize = 20.sp)
            }
        }
    }
}
