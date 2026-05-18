package com.example.androidcourse.data.cache

import android.content.Context
import com.example.androidcourse.data.constants.CacheConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.androidcourse.domain.model.Game
import java.io.File

open class GameCache(private val context: Context, private val lifeSeconds: Long = 15L) {

    private val gson = Gson()
    private val type = object : TypeToken<Map<String, CachedEntry>>() {}.type

    private data class CachedEntry(
        val data: List<Game>,
        val timestamp: Long
    )

    private fun cacheFile(): File = File(context.cacheDir, CacheConstants.CACHE_FILE_NAME)

    private fun now() = System.currentTimeMillis()

    suspend fun get(query: String): List<Game>? {
        val file = cacheFile()
        if (!file.exists()) return null
        val json = file.readText()
        val map: Map<String, CachedEntry> = gson.fromJson(json, type) ?: return null
        val entry = map[query] ?: return null
        if (now() - entry.timestamp > lifeSeconds * 1000) return null
        return entry.data
    }

    suspend fun put(query: String, data: List<Game>) {
        val file = cacheFile()
        val map: MutableMap<String, CachedEntry> = if (file.exists()) {
            gson.fromJson(file.readText(), type) ?: mutableMapOf()
        } else {
            mutableMapOf()
        }
        map[query] = CachedEntry(data, now())
        file.writeText(gson.toJson(map))
    }

    suspend fun getById(id: Int): Game? {
        val file = cacheFile()
        if (!file.exists()) return null
        val json = file.readText()
        val map: Map<String, CachedEntry> = gson.fromJson(json, type) ?: return null
        return map.values
            .filter { now() - it.timestamp <= lifeSeconds * 1000 }
            .flatMap { it.data }
            .firstOrNull { it.id == id }
    }
}