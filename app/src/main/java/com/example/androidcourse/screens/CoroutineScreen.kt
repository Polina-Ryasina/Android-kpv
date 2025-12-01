package com.example.androidcourse.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.androidcourse.data.Disp
import androidx.compose.ui.res.stringResource
import com.example.androidcourse.R

@Composable
fun AppRoot(viewModel: CoroutineViewModel) {
    val opts = viewModel.options
    val ctx = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    var open by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.toastMessage.value) {
        viewModel.toastMessage.value?.let {
            Toast.makeText(ctx, it, Toast.LENGTH_SHORT).show()
            viewModel.toastMessage.value = null
        }
    }

    LaunchedEffect(viewModel.showSnackbar.value) {
        if (viewModel.showSnackbar.value) {
            viewModel.snackbarMessage.value?.let { snackbarHostState.showSnackbar(it) }
            viewModel.showSnackbar.value = false
        }
    }

    DisposableEffect(lifecycleOwner, viewModel.allowBackground) {
        val observer = LifecycleEventObserver { _, event ->
            if (!viewModel.allowBackground) {
                when (event) {
                    Lifecycle.Event.ON_STOP -> viewModel.pauseOnBackground()
                    Lifecycle.Event.ON_START -> viewModel.resumeAfterPause()
                    else -> {}
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                Spacer(Modifier.height(24.dp))

                Text(stringResource(R.string.label_count, opts.count), modifier = Modifier.align(Alignment.CenterHorizontally))
                Slider(
                    value = opts.count.toFloat(),
                    valueRange = 10f..100f,
                    steps = 17,
                    onValueChange = { viewModel.setCount(it.toInt()) }
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedButton(onClick = { open = true }) { Text(opts.dispatcher.name) }
                    DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
                        Disp.entries.forEach {
                            DropdownMenuItem(text = { Text(it.name) }, onClick = { open = false; viewModel.chooseDispatcher(it) })
                        }
                    }
                }

                ToggleRow(stringResource(R.string.label_sequential), opts.sequential) { viewModel.setSequential(it) }
                ToggleRow(stringResource(R.string.label_parallel), opts.parallel) { viewModel.setParallel(it) }
                ToggleRow(stringResource(R.string.label_delayed_start), opts.delayedStart) { viewModel.setDelayed(it) }
                ToggleRow(stringResource(R.string.label_background_work), viewModel.allowBackground) { viewModel.setBackgroundAllowed(it) }

                Spacer(Modifier.height(40.dp))

                if (viewModel.running) {
                    Column {
                        LinearProgressIndicator(
                            progress = (viewModel.finishedCount.intValue + viewModel.failedCount.intValue) / viewModel.startedCount.toFloat(),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            if (!viewModel.running) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = { viewModel.startCoroutines() }) {
                    Text(stringResource(R.string.btn_start))
                }
            } else {
                Button(modifier = Modifier.fillMaxWidth(), onClick = { viewModel.stopAll() }) {
                    Text(stringResource(R.string.btn_stop))
                }
            }
        }
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onToggle: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label)
        Switch(checked = checked, onCheckedChange = onToggle)
    }
}