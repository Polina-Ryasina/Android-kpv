package com.example.androidcourse.domain.usecase

import com.example.androidcourse.data.repository.GameRepository
import com.example.androidcourse.domain.model.Game

class GetGameByIdUseCase(private val repository: GameRepository) {
    suspend operator fun invoke(id: Int): Game? {
        return repository.getGameById(id)
    }
}