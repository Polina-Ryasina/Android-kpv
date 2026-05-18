package com.example.androidcourse.di

import android.content.Context
import com.example.androidcourse.data.constants.PropertyConstants
import com.example.androidcourse.data.local.LocalDataSource
import com.example.androidcourse.data.remote.RemoteDataSource
import com.example.androidcourse.data.repository.GameRepository
import com.example.androidcourse.domain.usecase.FakeSearchGamesUseCase
import com.example.androidcourse.domain.usecase.SearchGamesUseCase
import com.example.androidcourse.presentation.viewmodel.DetailsViewModel
import com.example.androidcourse.presentation.viewmodel.SearchViewModel

class ServiceLocator(private val context: Context) {

    private val remote by lazy { RemoteDataSource(loadApiKey(context)) }
    private val local by lazy { LocalDataSource(context) }
    private val repository by lazy { GameRepository(remote, local) }
    private val searchUseCase by lazy { SearchGamesUseCase(repository) }
    //private val fakeSearchUseCase by lazy { FakeSearchGamesUseCase(searchUseCase) } // Для ошибки

    // fun searchViewModel(): SearchViewModel = SearchViewModel(fakeSearchUseCase) // Для ошибки
    fun searchViewModel(): SearchViewModel = SearchViewModel(searchUseCase) // Для ошибки
    fun detailsViewModel(): DetailsViewModel = DetailsViewModel()

    private fun loadApiKey(context: Context): String {
        val properties = java.util.Properties()
        context.assets.open(PropertyConstants.FILE_NAME).use { inputStream ->
            properties.load(inputStream)
        }
        return properties.getProperty(PropertyConstants.FIELD, "")
    }
}