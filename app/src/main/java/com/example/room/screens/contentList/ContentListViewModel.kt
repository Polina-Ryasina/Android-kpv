package com.example.room.screens.contentList

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.data.CharacterRepository
import com.example.room.db.entity.CharacterEntity
import com.example.room.screens.contentList.components.SortOption
import com.example.room.model.RaceOption
import com.example.room.model.ClassOption
import kotlinx.coroutines.launch

class ContentListViewModel(application: Application, private val repository: CharacterRepository) : AndroidViewModel(application) {

    var characters by mutableStateOf<List<CharacterEntity>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var currentSort by mutableStateOf<SortOption?>(null)
        private set

    var currentClassFilter by mutableStateOf<ClassOption?>(null)
        private set

    var currentRaceFilter by mutableStateOf<RaceOption?>(null)
        private set

    private var errorMessage by mutableStateOf<String?>(null)
        private set

    private var isLoadingData = false

    fun onSortOptionChange(sortOption: SortOption?) {
        if (sortOption != currentSort && !isLoadingData) {
            currentSort = sortOption
            currentClassFilter = null
            currentRaceFilter = null
            loadCharacters(sortOption)
        }
    }

    fun onClassFilterChange(newFilter: ClassOption?) {
        if (currentClassFilter != newFilter && !isLoadingData) {
            currentClassFilter = newFilter
            currentRaceFilter = null
            currentSort = null
            loadCharacters(currentSort)
        }
    }

    fun onRaceFilterChange(newFilter: RaceOption?) {
        if (currentRaceFilter != newFilter && !isLoadingData) {
            currentRaceFilter = newFilter
            currentClassFilter = null
            currentSort = null
            loadCharacters(currentSort)
        }
    }

    fun loadCharacters(sortOption: SortOption?) {
        if (isLoadingData) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            isLoadingData = true
            try {
                var list: List<CharacterEntity> = when {
                    currentClassFilter != null -> repository.getCharactersByClass(currentClassFilter!!.name)
                    currentRaceFilter != null -> repository.getCharactersByRace(currentRaceFilter!!.name)
                    else -> when (sortOption) {
                        SortOption.NameAsc -> repository.getCharactersSortedByNameAsc()
                        SortOption.NameDesc -> repository.getCharactersSortedByNameDesc()
                        SortOption.LevelAsc -> repository.getCharactersSortedByLevelAsc()
                        SortOption.LevelDesc -> repository.getCharactersSortedByLevelDesc()
                        SortOption.StrengthAsc -> repository.getCharactersSortedByStrengthAsc()
                        SortOption.StrengthDesc -> repository.getCharactersSortedByStrengthDesc()
                        SortOption.AgilityAsc -> repository.getCharactersSortedByAgilityAsc()
                        SortOption.AgilityDesc -> repository.getCharactersSortedByAgilityDesc()
                        SortOption.IntelligenceAsc -> repository.getCharactersSortedByIntelligenceAsc()
                        SortOption.IntelligenceDesc -> repository.getCharactersSortedByIntelligenceDesc()
                        null -> repository.getAllCharacters()
                    }
                }
                characters = list

            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
                isLoadingData = false
            }
        }
    }
}