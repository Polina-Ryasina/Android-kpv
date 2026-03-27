package com.example.androidcourse.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.androidcourse.R
import com.example.androidcourse.presentation.viewmodel.DetailsViewModel
import com.example.androidcourse.utils.filterUnknown
import com.example.androidcourse.utils.orHideUnknown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsScreen(
    viewModel: DetailsViewModel,
    onBack: () -> Unit
) {
    val game = viewModel.game

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.game_details_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button))
                    }
                }
            )
        }
    ) { padding ->
        if (game == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center
            ) {
                Text(stringResource(R.string.no_game_selected), textAlign = TextAlign.Center)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            AsyncImage(
                model = game.backgroundImage,
                contentDescription = game.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = game.name,
                style = MaterialTheme.typography.headlineMedium
            )

            game.released?.orHideUnknown()?.let { released ->
                Text(
                    text = stringResource(R.string.released_label, released),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = stringResource(R.string.rating_label, game.rating, game.ratingTop),
                style = MaterialTheme.typography.bodyMedium
            )

            val platforms = game.platforms.filterUnknown()
            if (platforms.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.platforms_label, platforms.joinToString(", ")),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            val genres = game.genres.filterUnknown()
            if (genres.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.genres_label, genres.joinToString(", ")),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            val developers = game.developers.filterUnknown()
            if (developers.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.developers_label, developers.joinToString(", ")),
                    style = MaterialTheme.typography.bodyMedium
                )
            }


            val publishers = game.publishers.filterUnknown()
            if (publishers.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.publishers_label, publishers.joinToString(", ")),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
