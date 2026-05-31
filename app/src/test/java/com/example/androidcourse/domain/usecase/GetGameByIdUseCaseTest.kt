package com.example.androidcourse.domain.usecase

import com.example.androidcourse.data.repository.GameRepository
import com.example.androidcourse.domain.model.Game
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetGameByIdUseCaseTest {

    private lateinit var repository: GameRepository
    private lateinit var useCase: GetGameByIdUseCase

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
        useCase = GetGameByIdUseCase(repository)
    }

    @Test
    fun `invoke - вызывает репозиторий и возвращает корректную игру по id`() = runTest {
        val game = makeGame(42, "Witcher 3")
        coEvery { repository.getGameById(42) } returns game

        val result = useCase(42)

        coVerify(exactly = 1) { repository.getGameById(42) }
        assertEquals(game, result)
    }
}