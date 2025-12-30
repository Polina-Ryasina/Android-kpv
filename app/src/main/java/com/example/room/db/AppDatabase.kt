package com.example.room.db;

import android.content.Context
import androidx.room.Database;
import androidx.room.Room
import androidx.room.RoomDatabase;

import com.example.room.db.dao.CharacterDao;
import com.example.room.db.dao.UserDao;
import com.example.room.db.entity.CharacterEntity;
import com.example.room.db.entity.UserEntity;

@Database(
    entities = [UserEntity::class, CharacterEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}