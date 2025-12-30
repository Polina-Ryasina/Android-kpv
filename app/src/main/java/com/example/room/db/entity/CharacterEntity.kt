package com.example.room.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "race")
    val race: String,

    @ColumnInfo(name = "character_class")
    val characterClass: String,

    @ColumnInfo(name = "level")
    val level: Int,

    @ColumnInfo(name = "strength")
    val strength: Int,

    @ColumnInfo(name = "agility")
    val agility: Int,

    @ColumnInfo(name = "intelligence")
    val intelligence: Int,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "created_at")
    val createdAt: Long
)