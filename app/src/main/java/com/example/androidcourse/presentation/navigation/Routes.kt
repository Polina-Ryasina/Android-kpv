package com.example.androidcourse.presentation.navigation

object Routes {

    const val SEARCH = "search"
    const val DETAILS = "details/{gameId}"

    fun details(gameId: Int) = "details/$gameId"
}