package com.example.androidcourse.di

import android.content.Context
import com.example.androidcourse.data.constants.PropertyConstants
import com.example.androidcourse.data.local.LocalDataSource
import com.example.androidcourse.data.remote.RemoteDataSource
import com.example.androidcourse.data.repository.GameRepository
import com.example.androidcourse.domain.usecase.GetGameByIdUseCase
import com.example.androidcourse.domain.usecase.SearchGamesUseCase
import com.example.androidcourse.presentation.viewmodel.DetailsViewModel
import com.example.androidcourse.presentation.viewmodel.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import java.util.Properties

val appModule = module {

    single { loadApiKey(androidContext()) }

    single { RemoteDataSource(get()) }
    single { LocalDataSource(androidContext()) }
    single { GameRepository(get(), get()) }
    single { SearchGamesUseCase(get()) }
    single { GetGameByIdUseCase(get()) }

    viewModelOf(::SearchViewModel)

    viewModel { (gameId: Int) ->
        DetailsViewModel(gameId, get())
    }
}

private fun loadApiKey(context: Context): String {
    val properties = Properties()
    context.assets.open(PropertyConstants.FILE_NAME).use { properties.load(it) }
    return properties.getProperty(PropertyConstants.FIELD, "")
}