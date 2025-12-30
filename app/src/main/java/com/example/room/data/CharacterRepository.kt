package com.example.room.data

import com.example.room.db.dao.CharacterDao
import com.example.room.db.entity.CharacterEntity

class CharacterRepository(private val characterDao: CharacterDao) {

    suspend fun addCharacter(character: CharacterEntity) {
        characterDao.insertCharacter(character)
    }

    suspend fun deleteCharacter(characterId: Int) {
        characterDao.deleteCharacter(characterId)
    }

    suspend fun getAllCharacters(): List<CharacterEntity> {
        return characterDao.getAllCharacters()
    }

    suspend fun getCharactersByUser(userId: Int): List<CharacterEntity> {
        return characterDao.getCharactersByUser(userId)
    }

    suspend fun getCharactersSortedByNameAsc() =
        characterDao.getCharactersSortedByNameAsc()

    suspend fun getCharactersSortedByNameDesc() =
        characterDao.getCharactersSortedByNameDesc()

    suspend fun getCharactersSortedByLevelAsc() =
        characterDao.getCharactersSortedByLevelAsc()

    suspend fun getCharactersSortedByLevelDesc() =
        characterDao.getCharactersSortedByLevelDesc()

    suspend fun getCharactersSortedByStrengthAsc() =
        characterDao.getCharactersSortedByStrengthAsc()

    suspend fun getCharactersSortedByStrengthDesc() =
        characterDao.getCharactersSortedByStrengthDesc()

    suspend fun getCharactersSortedByAgilityAsc() =
        characterDao.getCharactersSortedByAgilityAsc()

    suspend fun getCharactersSortedByAgilityDesc() =
        characterDao.getCharactersSortedByAgilityDesc()

    suspend fun getCharactersSortedByIntelligenceAsc() =
        characterDao.getCharactersSortedByIntelligenceAsc()

    suspend fun getCharactersSortedByIntelligenceDesc() =
        characterDao.getCharactersSortedByIntelligenceDesc()

    suspend fun getCharactersByRace(race: String) =
        characterDao.getCharactersByRace(race)

    suspend fun getCharactersByClass(characterClass: String) =
        characterDao.getCharactersByClass(characterClass)
}