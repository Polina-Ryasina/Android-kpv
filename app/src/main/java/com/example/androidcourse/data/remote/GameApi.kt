package com.example.androidcourse.data.remote

import com.example.androidcourse.data.constants.ApiConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {

    @GET(ApiConstants.GET)
    suspend fun searchGames(
        @Query(ApiConstants.SEARCH) query: String,
        @Query(ApiConstants.KEY) key: String
    ): RawgGamesResponse
}

data class RawgGamesResponse(
    val results: List<RawgGameDto>
)

data class RawgGameDto(
    val id: Int,
    val name: String,
    val released: String?,
    val background_image: String?,
    val rating: Float,
    val rating_top: Int,
    val ratings_count: Int,
    val platforms: List<RawgPlatformDto>?,
    val genres: List<RawgGenreDto>?,
    val developers: List<RawgDeveloperDto>?,
    val publishers: List<RawgPublisherDto>?
)

data class RawgPlatformDto(
    val platform: RawgSimplePlatformDto
)

data class RawgSimplePlatformDto(
    val name: String
)

data class RawgGenreDto(
    val name: String
)

data class RawgDeveloperDto(
    val name: String
)

data class RawgPublisherDto(
    val name: String
)