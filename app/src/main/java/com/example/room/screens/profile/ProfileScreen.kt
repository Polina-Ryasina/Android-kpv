package com.example.room.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.room.R
import com.example.room.screens.contentList.components.CharacterCard

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onLogout: () -> Unit
) {
    Scaffold { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
        ) {

            if (viewModel.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                return@Scaffold
            }

            viewModel.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
                return@Scaffold
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.profile_label, viewModel.user?.nickname ?: stringResource(R.string.empty)),
                    fontSize = 30.sp, fontWeight = FontWeight.Bold)

                Row {
                    IconButton(onClick = { viewModel.deleteAccount() }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete_button))
                    }
                    IconButton(onClick = {
                        onLogout()
                    }) {
                        Icon(imageVector = Icons.Default.Logout, contentDescription = stringResource(R.string.logout_button))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = stringResource(R.string.added_characters, viewModel.characters.size), fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.characters.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.no_user_characters),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    items(viewModel.characters) { character ->
                        CharacterCard(character)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            if (viewModel.pendingDelete) {
                AlertDialog(
                    onDismissRequest = { viewModel.cancelDelete() },
                    title = {
                        Text(text = stringResource(R.string.delete_account))
                    },
                    text = {
                        Text(text = stringResource(R.string.account_deleted_warning))
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.confirmDeleteAccount()
                                onLogout()
                            }
                        ) {
                            Text(stringResource(R.string.delete))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.cancelDelete() }) {
                            Text(stringResource(R.string.cancel))
                        }
                    }
                )
            }
        }
    }
}