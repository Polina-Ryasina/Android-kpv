package com.example.androidcourse.domain.usecase

import com.example.androidcourse.data.repository.GameRepository
import com.example.androidcourse.domain.model.Game
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchGamesUseCaseTest {

    private lateinit var repository: GameRepository
    private lateinit var useCase: SearchGamesUseCase

    private fun makeGame(id: Int, name: String) = Game(
        id = id,
        name = name,
        released = null,
        backgroundImage = null,
        rating = 0f,
        ratingTop = 0,
        ratingsCount = 0,
        platforms = emptyList<String>().toImmutableList(),
        genres = emptyList<String>().toImmutableList(),
        developers = emptyList<String>().toImmutableList(),
        publishers = emptyList<String>().toImmutableList()
    )

    @Before
    fun setUp() {
        repository = mockk()
        useCase = SearchGamesUseCase(repository)
    }

    @Test
    fun `invoke - вызывает репозиторий ровно один раз и возвращает корректный список`() = runTest {
        val games = listOf(makeGame(1, "Witcher 3"), makeGame(2, "Cyberpunk 2077"))
        coEvery { repository.searchGames("witcher") } returns Result.success(games to "Server")

        val result = useCase("witcher")

        coVerify(exactly = 1) { repository.searchGames("witcher") }
        assertEquals(games, result.getOrNull()?.first)
        assertEquals("Server", result.getOrNull()?.second)
    }

    @Test
    fun `invoke - возвращает пустой список если репозиторий вернул пустой список`() = runTest {
        coEvery { repository.searchGames(any()) } returns Result.success(emptyList<Game>() to "Server")

        val result = useCase("игра")
        coVerify(exactly = 1) { repository.searchGames("игра") }
        assertTrue(result.getOrNull()?.first.isNullOrEmpty())
    }
}