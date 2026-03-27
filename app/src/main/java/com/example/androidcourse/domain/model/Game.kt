package com.example.androidcourse.domain.model

data class Game(
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