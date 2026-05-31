package com.example.androidcourse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.domain.model.Game
import com.example.androidcourse.domain.usecase.GetGameByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val gameId: Int,
    private val getGameByIdUseCase: GetGameByIdUseCase
) : ViewModel() {

    private val _game = MutableStateFlow<Game?>(null)
    val game: StateFlow<Game?> = _game

    init {
        viewModelScope.launch {
            _game.value = getGameByIdUseCase(gameId)
        }
    }
}