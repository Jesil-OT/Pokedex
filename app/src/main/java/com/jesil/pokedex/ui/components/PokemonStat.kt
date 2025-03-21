package com.jesil.pokedex.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jesil.pokedex.data.remote.responses.Pokemon
import com.jesil.pokedex.util.parseStatToAbbr
import com.jesil.pokedex.util.parseStatToColor


@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 30.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0,
    modifier: Modifier = Modifier
) {
    val animationPlayed = remember {
        mutableStateOf(false)
    }
    val currentPercent = animateFloatAsState(
        targetValue = if (animationPlayed.value) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        ), label = "currentPercent"
    )

    LaunchedEffect(key1 = true) {
        animationPlayed.value = true
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) {
                    Color(0xFF505050)
                } else {
                    Color.White
                }
            ),
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(currentPercent.value)
                    .clip(CircleShape)
                    .background(statColor)
                    .padding(horizontal = 8.dp),
                content = {
                    Text(
                        text = statName,
                        color = Color.Black,
                    )
                    Text(
                        text = (currentPercent.value * statMaxValue).toInt().toString(),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    )
}

@Composable
fun PokemonBaseStats(
    modifier: Modifier = Modifier,
    pokemonInfo: Pokemon,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemonInfo.stats.maxOf { it.base_stat }
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        content = {
            for (i in pokemonInfo.stats.indices) {
                val stat = pokemonInfo.stats[i]
                PokemonStat(
                    statName = parseStatToAbbr(stat),
                    statValue = stat.base_stat,
                    statMaxValue = maxBaseStat,
                    statColor = parseStatToColor(stat),
                    animDelay = i * animDelayPerItem
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    )


}