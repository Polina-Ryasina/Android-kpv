package com.example.room.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.room.db.AppDatabase
import com.example.room.data.UserRepository

class DeleteNotRestoredAccounts(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val db = AppDatabase.getDatabase(applicationContext)
        val userRepository = UserRepository(db.userDao())
        userRepository.deleteNotRestoredAccounts()
        return Result.success()
    }
}