package com.example.androidcourse.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.R
import com.example.androidcourse.data.constants.ErrorConstants
import com.example.androidcourse.domain.model.Game
import com.example.androidcourse.domain.usecase.SearchGamesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchUseCase: SearchGamesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Empty)
    val state: StateFlow<State> = _state

    fun searchGames(query: String) {
        val normalizedQuery = query.lowercase().trim()
        if (normalizedQuery.isBlank()) {
            _state.value = State.Empty
            return
        }
        viewModelScope.launch {
            _state.value = State.Loading
            val result = searchUseCase(normalizedQuery)
            _state.value = result.fold(
                onSuccess = { (games, source) -> State.Success(games, source) },
                onFailure = { throwable ->
                    val errorMessage = throwable.message
                    State.Error(resId = mapErrorToResId(errorMessage))
                }
            )
        }
    }

    sealed class State {
        object Empty : State()
        object Loading : State()
        data class Success(val games: List<Game>, val source: String) : State()
        data class Error(val message: String? = null, @StringRes val resId: Int? = null) : State()
    }

    @StringRes
    private fun mapErrorToResId(errorMessage: String?): Int {
        return when {
            errorMessage == null || errorMessage.isBlank() -> R.string.unknown_error
            ErrorConstants.NOT_FOUND in errorMessage || ErrorConstants.NOT_FOUND_TEXT in errorMessage -> R.string.no_games_found
            ErrorConstants.TIMEOUT in errorMessage -> R.string.timeout_error
            ErrorConstants.NETWORK in errorMessage -> R.string.network_error
            else -> R.string.unknown_error
        }
    }
}