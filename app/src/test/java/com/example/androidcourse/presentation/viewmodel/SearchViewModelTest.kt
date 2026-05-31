package com.example.androidcourse.presentation.viewmodel

import com.example.androidcourse.domain.model.Game
import com.example.androidcourse.domain.usecase.SearchGamesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var useCase: SearchGamesUseCase
    private lateinit var viewModel: SearchViewModel

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
        Dispatchers.setMain(testDispatcher)
        useCase = mockk()
        viewModel = SearchViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchGames - успешный результат передаётся в State Success`() = runTest {
        val games = listOf(makeGame(1, "Witcher 3"), makeGame(2, "Cyberpunk 2077"))
        coEvery { useCase(any()) } returns Result.success(games to "Server")

        viewModel.searchGames("witcher")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(SearchViewModel.State.Success(games.toImmutableList(), "Server"), state)
    }

    @Test
    fun `clearSearch - стейт сбрасывается в Empty`() = runTest {
        val games = listOf(makeGame(1, "Witcher 3"))
        coEvery { useCase(any()) } returns Result.success(games to "Server")

        viewModel.searchGames("witcher")
        advanceUntilIdle()

        viewModel.clearSearch()
        assertEquals(SearchViewModel.State.Empty, viewModel.state.value)
    }
}