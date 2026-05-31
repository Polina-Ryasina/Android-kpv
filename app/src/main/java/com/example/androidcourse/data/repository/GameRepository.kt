package com.example.androidcourse.data.repository

import com.example.androidcourse.data.constants.SourceConstants
import com.example.androidcourse.data.local.LocalDataSource
import com.example.androidcourse.domain.model.Game
import com.example.androidcourse.data.remote.RemoteDataSource

class GameRepository(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) {

    suspend fun searchGames(query: String): Result<Pair<List<Game>, String>> {
        try {
            val cached = local.get(query)
            if (cached != null) {
                return Result.success(cached to SourceConstants.CACHE)
            }
            val fresh = remote.searchGames(query)
            local.put(query, fresh)
            return Result.success(fresh to SourceConstants.SERVER)
        } catch (t: Throwable) {
            return Result.failure(t)
        }
    }

    suspend fun getGameById(id: Int): Game? {
        return local.getById(id)
    }
}