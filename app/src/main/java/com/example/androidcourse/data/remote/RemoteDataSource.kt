package com.example.androidcourse.data.remote

import com.example.androidcourse.data.constants.ApiConstants
import com.example.androidcourse.domain.model.Game
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource(private val apiKey: String) {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GameApi::class.java)

    suspend fun searchGames(query: String): List<Game> {
        val response = api.searchGames(query, apiKey)
        return response.results.map { dto ->
            Game(
                id = dto.id,
                name = dto.name,
                released = dto.released,
                backgroundImage = dto.background_image,
                rating = dto.rating,
                ratingTop = dto.rating_top,
                ratingsCount = dto.ratings_count,
                platforms = dto.platforms?.map { it.platform.name }?.ifEmpty { listOf(ApiConstants.UNKNOWN_VALUE) } ?: listOf(ApiConstants.UNKNOWN_VALUE),
                genres = dto.genres?.map { it.name }?.ifEmpty { listOf(ApiConstants.UNKNOWN_VALUE) } ?: listOf(ApiConstants.UNKNOWN_VALUE),
                developers = dto.developers?.map { it.name }?.ifEmpty { listOf(ApiConstants.UNKNOWN_VALUE) } ?: listOf(ApiConstants.UNKNOWN_VALUE),
                publishers = dto.publishers?.map { it.name }?.ifEmpty { listOf(ApiConstants.UNKNOWN_VALUE) } ?: listOf(ApiConstants.UNKNOWN_VALUE)
            )
        }
    }
}