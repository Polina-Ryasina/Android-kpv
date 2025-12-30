package com.example.room.data

import com.example.room.db.dao.UserDao
import com.example.room.db.entity.UserEntity
import java.util.concurrent.TimeUnit

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }

    suspend fun updateDeleteDate(userId: Int) {
        return userDao.updateDeleteDate(userId, System.currentTimeMillis())
    }

    suspend fun isDeletedRecently(user: UserEntity, days: Int = 7): Boolean {
        val deletedAt = user.deletedAt ?: return false
        val currentTime = System.currentTimeMillis()
        val diff = currentTime - deletedAt
        return diff <= TimeUnit.DAYS.toMillis(days.toLong())
    }

    suspend fun restoreUser(userId: Int) {
        userDao.restoreUser(userId)
    }

    suspend fun deleteUser(userId: Int) {
        userDao.deleteUser(userId)
    }

    suspend fun deleteNotRestoredAccounts() {
        val threshold = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7.toLong())
        val usersToDelete = userDao.getUsersMarkedForDeletion(threshold)
        usersToDelete.forEach { user ->
            deleteUser(user.id)
        }
    }
}