package com.example.androidcourse.domain.usecase

import com.example.androidcourse.domain.model.Game

class FakeSearchGamesUseCase(private val searchUseCase: SearchGamesUseCase) {

    private var requestCount = 0

    suspend operator fun invoke(query: String): Result<Pair<List<Game>, String>> {
        requestCount++

        return if (requestCount % 3 == 0) {
            Result.failure(Exception("404: Not Found"))
        } else {
            searchUseCase(query)
        }
    }
}