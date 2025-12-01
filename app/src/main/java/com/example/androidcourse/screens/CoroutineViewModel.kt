package com.example.androidcourse.screens

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.R
import com.example.androidcourse.data.*
import kotlinx.coroutines.*
import kotlin.random.Random

class CoroutineViewModel(application: Application) : AndroidViewModel(application) {

    private val ctx = application.applicationContext

    var options by mutableStateOf(CoroutineOptions())
        private set

    var running by mutableStateOf(false)
        private set

    var startedCount by mutableStateOf(0)
        private set

    var finishedCount = mutableIntStateOf(0)
    var failedCount = mutableIntStateOf(0)
    var stoppedCount = mutableIntStateOf(0)

    var toastMessage = mutableStateOf<String?>(null)
    var showSnackbar = mutableStateOf(false)
    var snackbarMessage = mutableStateOf<String?>(null)

    var allowBackground by mutableStateOf(true)

    private var rootJob: Job? = null

    private val pendingTasks = mutableListOf<suspend () -> Unit>()
    private val activeJobs = mutableListOf<Job>()

    fun setCount(count: Int) {
        val aligned = (count.coerceIn(10, 100) / 5) * 5
        options = options.copy(count = aligned)
    }

    fun chooseDispatcher(disp: Disp) {
        options = options.copy(dispatcher = disp)
    }

    fun setSequential(flag: Boolean) {
        options = options.copy(sequential = flag, parallel = !flag)
    }

    fun setParallel(flag: Boolean) {
        options = options.copy(parallel = flag, sequential = !flag)
    }

    fun setDelayed(flag: Boolean) {
        options = options.copy(delayedStart = flag)
    }

    fun setBackgroundAllowed(flag: Boolean) {
        allowBackground = flag
    }

    fun startCoroutines(userOptions: CoroutineOptions? = null, resetCounters: Boolean = true) {
        if (running) return
        running = true

        val cfg = userOptions ?: options

        if (resetCounters) {
            finishedCount.intValue = 0
            failedCount.intValue = 0
            stoppedCount.intValue = 0
            pendingTasks.clear()
            startedCount = cfg.count
        }

        val dispatcher = resolveDispatcher(cfg.dispatcher)

        if (pendingTasks.isEmpty()) {
            repeat(cfg.count) {
                pendingTasks.add {
                    try { runCoroutine(dispatcher) }
                    catch (e: Exception) { handleException(e) }
                }
            }
        }

        activeJobs.clear()

        rootJob = viewModelScope.launch {

            if (cfg.delayedStart) delay(5000)

            if (cfg.sequential) {

                while (pendingTasks.isNotEmpty()) {
                    val task = pendingTasks[0]
                    pendingTasks.removeAt(0)
                    val job = launch(dispatcher) { task() }
                    activeJobs.add(job)
                    job.join()
                }

            } else {

                val jobs = pendingTasks.map { task ->
                    launch(dispatcher) {
                        task()
                        pendingTasks.remove(task)
                    }
                }
                activeJobs.addAll(jobs)
                activeJobs.joinAll()

            }

            withContext(Dispatchers.Main) {
                running = false
            }
        }
    }

    private fun resolveDispatcher(disp: Disp): CoroutineDispatcher {
        return when (disp) {
            Disp.DEFAULT -> Dispatchers.Default
            Disp.IO -> Dispatchers.IO
            Disp.MAIN -> Dispatchers.Main
            Disp.UNCONFINED -> Dispatchers.Unconfined
        }
    }

    private suspend fun runCoroutine(dispatcher: CoroutineDispatcher) {
        withContext(dispatcher) {

            val wait = Random.nextLong(1000L, 10_000L)
            delay(wait)

            if (wait >= 7000L && Random.nextFloat() < 0.3f) {
                when (Random.nextInt(3)) {
                    0 -> throw ToastException(ctx.getString(R.string.error_toast))
                    1 -> throw SnackbarException(ctx.getString(R.string.error_snackbar))
                    else -> throw ResetException(ctx.getString(R.string.error_reset))
                }
            }

            withContext(Dispatchers.Main) {
                finishedCount.intValue++
            }
        }
    }

    fun stopAll() {
        rootJob?.cancel()
        pendingTasks.clear()
        activeJobs.clear()
        viewModelScope.launch {
            rootJob?.join()
            val total = options.count
            stoppedCount.intValue = total - finishedCount.intValue - failedCount.intValue
            toastMessage.value = ctx.getString(R.string.msg_cancelled, stoppedCount.intValue)
            running = false
        }
        startedCount = 0
    }

    private suspend fun handleException(e: Exception) {
        withContext(Dispatchers.Main) {
            when (e) {
                is ToastException -> toastMessage.value = e.message
                is SnackbarException -> {
                    snackbarMessage.value = e.message
                    showSnackbar.value = true
                }

                is ResetException -> {
                    options = CoroutineOptions()
                    toastMessage.value = e.message
                }
            }
            failedCount.intValue++
        }
    }

    fun pauseOnBackground() {
        if (!running) return

        activeJobs.forEach { it.cancel() }
        activeJobs.clear()
        rootJob?.cancel()

        running = false
    }

    fun resumeAfterPause() {
        if (pendingTasks.isEmpty()) return
        startCoroutines(resetCounters = false)
    }
}