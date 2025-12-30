package com.example.room.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.room.db.entity.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Query("DELETE FROM characters WHERE id = :id")
    suspend fun deleteCharacter(id: Int)

    @Query("SELECT * FROM characters")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE user_id = :userId")
    suspend fun getCharactersByUser(userId: Int): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY name ASC")
    suspend fun getCharactersSortedByNameAsc(): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY name DESC")
    suspend fun getCharactersSortedByNameDesc(): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY level ASC")
    suspend fun getCharactersSortedByLevelAsc(): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY level DESC")
    suspend fun getCharactersSortedByLevelDesc(): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY strength ASC")
    suspend fun getCharactersSortedByStrengthAsc(): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY strength DESC")
    suspend fun getCharactersSortedByStrengthDesc(): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY agility ASC")
    suspend fun getCharactersSortedByAgilityAsc(): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY agility DESC")
    suspend fun getCharactersSortedByAgilityDesc(): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY intelligence ASC")
    suspend fun getCharactersSortedByIntelligenceAsc(): List<CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY intelligence DESC")
    suspend fun getCharactersSortedByIntelligenceDesc(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE race = :race")
    suspend fun getCharactersByRace(race: String): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE character_class = :characterClass")
    suspend fun getCharactersByClass(characterClass: String): List<CharacterEntity>
}