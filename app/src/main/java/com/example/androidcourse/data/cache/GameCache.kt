package com.example.androidcourse.data.cache

import android.content.Context
import com.example.androidcourse.data.constants.CacheConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.androidcourse.domain.model.Game
import kotlinx.collections.immutable.toImmutableList
import java.io.File

open class GameCache(private val context: Context, private val lifeSeconds: Long = 15L) {

    private val gson = Gson()
    private val type = object : TypeToken<Map<String, CachedEntry>>() {}.type

    private data class CachedEntry(
        val data: List<GameJson>,
        val timestamp: Long
    )

    private data class GameJson(
        val id: Int,
        val name: String,
        val released: String?,
        val backgroundImage: String?,
        val rating: Float,
        val ratingTop: Int,
        val ratingsCount: Int,
        val platforms: List<String>,
        val genres: List<String>,
        val developers: List<String>,
        val publishers: List<String>
    )

    private fun File.readMap(): Map<String, CachedEntry>? = if (exists()) gson.fromJson(readText(), type) else null

    private fun cacheFile(): File = File(context.cacheDir, CacheConstants.CACHE_FILE_NAME)

    private fun now() = System.currentTimeMillis()

    private fun GameJson.toDomain() = Game(
        id = id,
        name = name,
        released = released,
        backgroundImage = backgroundImage,
        rating = rating,
        ratingTop = ratingTop,
        ratingsCount = ratingsCount,
        platforms = platforms.toImmutableList(),
        genres = genres.toImmutableList(),
        developers = developers.toImmutableList(),
        publishers = publishers.toImmutableList()
    )

    private fun Game.toJson() = GameJson(
        id = id,
        name = name,
        released = released,
        backgroundImage = backgroundImage,
        rating = rating,
        ratingTop = ratingTop,
        ratingsCount = ratingsCount,
        platforms = platforms.toList(),
        genres = genres.toList(),
        developers = developers.toList(),
        publishers = publishers.toList()
    )

    suspend fun get(query: String): List<Game>? {
        val map = cacheFile().readMap() ?: return null
        val entry = map[query] ?: return null
        if (now() - entry.timestamp > lifeSeconds * 1000) return null
        return entry.data.map { it.toDomain() }
    }

    suspend fun put(query: String, data: List<Game>) {
        val file = cacheFile()
        val map: MutableMap<String, CachedEntry> = file.readMap()?.toMutableMap() ?: mutableMapOf()
        map[query] = CachedEntry(data.map { it.toJson() }, now())
        file.writeText(gson.toJson(map))
    }

    suspend fun getById(id: Int): Game? {
        val map = cacheFile().readMap() ?: return null
        return map.values
            .filter { now() - it.timestamp <= lifeSeconds * 1000 }
            .flatMap { it.data }
            .firstOrNull { it.id == id }
            ?.toDomain()
    }
}