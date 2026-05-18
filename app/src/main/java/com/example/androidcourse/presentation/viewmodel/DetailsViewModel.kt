package com.example.androidcourse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidcourse.domain.model.Game

class DetailsViewModel : ViewModel() {

    private var _game: Game? = null
    val game: Game? get() = _game

    fun setGame(game: Game) {
        _game = game
    }
}