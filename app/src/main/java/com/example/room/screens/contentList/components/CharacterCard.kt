package com.example.room.screens.contentList.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.room.R
import com.example.room.db.entity.CharacterEntity
import com.example.room.model.ClassOption
import com.example.room.model.RaceOption

@Composable
fun CharacterCard(character: CharacterEntity) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = character.name, fontSize = 28.sp, fontWeight = FontWeight.Medium)

            val className = try {
                ClassOption.valueOf(character.characterClass).displayName
            } catch (e: Exception) {
                character.characterClass
            }
            val raceName = try {
                RaceOption.valueOf(character.race).displayName
            } catch (e: Exception) {
                character.race
            }

            Text(text = stringResource(R.string.card_profession, className, raceName, character.level), fontSize = 18.sp)
            Text(text = stringResource(R.string.card_parameters, character.strength, character.agility, character.intelligence), fontSize = 18.sp)

            Spacer(modifier = Modifier.height(6.dp))

            if (character.description != null) {
                Text(stringResource(R.string.card_description, character.description), fontSize = 16.sp)
            }
        }
    }
}