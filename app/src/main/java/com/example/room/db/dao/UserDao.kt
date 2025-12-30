package com.example.room.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.room.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE users SET deleted_at = :timestamp WHERE id = :userId")
    suspend fun updateDeleteDate(userId: Int, timestamp: Long)

    @Query("UPDATE users SET deleted_at = NULL WHERE id = :userId")
    suspend fun restoreUser(userId: Int)

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUser(userId: Int)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM users WHERE deleted_at IS NOT NULL AND deleted_at < :threshold")
    suspend fun getUsersMarkedForDeletion(threshold: Long): List<UserEntity>
}