package com.example.androidcourse.domain.usecase

import com.example.androidcourse.data.repository.GameRepository
import com.example.androidcourse.domain.model.Game

class SearchGamesUseCase(private val repository: GameRepository) {

    suspend operator fun invoke(query: String): Result<Pair<List<Game>, String>> {
        return repository.searchGames(query)
            .map { (list, source) -> list.map { dtoToDomain(it) } to source }
    }

    private fun dtoToDomain(dto: Game): Game = dto
}