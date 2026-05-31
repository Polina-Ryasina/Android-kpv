package com.example.androidcourse.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class Game(
    val id: Int,
    val name: String,
    val released: String?,
    val backgroundImage: String?,
    val rating: Float,
    val ratingTop: Int,
    val ratingsCount: Int,
    val platforms: ImmutableList<String>,
    val genres: ImmutableList<String>,
    val developers: ImmutableList<String>,
    val publishers: ImmutableList<String>

)