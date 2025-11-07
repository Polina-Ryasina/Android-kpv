import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.androidcourse.R
import com.example.androidcourse.data.OptionsEnum
import com.example.androidcourse.ui.screens.firstscreen.FirstScreenViewModel

@Composable
fun FirstScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
    bottomBarHeight: Int
) {
    val context = LocalContext.current
    val viewModel = remember { FirstScreenViewModel(context) }
    val colors = MaterialTheme.colorScheme

    var expandedPriority by remember { mutableStateOf(false) }
    val priorities = listOf(
        OptionsEnum.Low,
        OptionsEnum.Medium,
        OptionsEnum.High,
        OptionsEnum.Urgent
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colors.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Top
            ) {
                OutlinedTextField(
                    value = viewModel.title,
                    onValueChange = { viewModel.onTitleChange(it) },
                    label = { Text(stringResource(R.string.title_label), color = colors.onBackground) },
                    isError = viewModel.titleError != null,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = colors.onBackground)
                )
                viewModel.titleError?.let {
                    Text(it, color = colors.error, modifier = Modifier.padding(top = 4.dp))
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.body,
                    onValueChange = { viewModel.onBodyChange(it) },
                    label = { Text(stringResource(R.string.body_label), color = colors.onBackground) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp, max = 160.dp),
                    textStyle = LocalTextStyle.current.copy(color = colors.onBackground)
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(stringResource(R.string.expand_long_text), color = colors.onBackground)
                        if (viewModel.body.isBlank()) {
                            Text(
                                stringResource(R.string.expand_hint),
                                fontSize = 12.sp,
                                color = colors.onBackground,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    Switch(
                        checked = viewModel.expandIfLong,
                        onCheckedChange = { viewModel.onExpandChange(it) },
                        enabled = viewModel.body.isNotBlank(),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.primary,
                            uncheckedThumbColor = colors.secondary
                        )
                    )
                }

                Spacer(Modifier.height(16.dp))

                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            stringResource(R.string.priority),
                            modifier = Modifier.weight(1f),
                            color = colors.onBackground
                        )

                        Box(contentAlignment = Alignment.TopEnd) {
                            OutlinedButton(
                                onClick = { expandedPriority = true },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = colors.primary
                                )
                            ) {
                                Text(viewModel.selectedPriority.name, color = colors.primary)
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = colors.primary
                                )
                            }

                            DropdownMenu(
                                expanded = expandedPriority,
                                onDismissRequest = { expandedPriority = false },
                            ) {
                                priorities.forEach { p ->
                                    DropdownMenuItem(
                                        text = { Text(p.name, color = colors.onBackground) },
                                        onClick = {
                                            viewModel.onPriorityChange(p)
                                            expandedPriority = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.open_main), modifier = Modifier.weight(1f), color = colors.onBackground)
                    Switch(
                        checked = viewModel.openMainOnClick,
                        onCheckedChange = { viewModel.onOpenMainChange(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.primary,
                            uncheckedThumbColor = colors.secondary
                        )
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.add_reply), modifier = Modifier.weight(1f), color = colors.onBackground)
                    Switch(
                        checked = viewModel.addReply,
                        onCheckedChange = { viewModel.onAddReplyChange(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.primary,
                            uncheckedThumbColor = colors.secondary
                        )
                    )
                }
            }

            Button(
                onClick = {
                    viewModel.createNotification()
                    viewModel.onTitleChange("", true)
                    viewModel.onBodyChange("")
                    viewModel.onExpandChange(false)
                    viewModel.onOpenMainChange(true)
                    viewModel.onAddReplyChange(false)
                    viewModel.onPriorityChange(OptionsEnum.Medium)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Text(stringResource(R.string.create_notification))
            }
        }
    }
}
