package com.jesil.pokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.pokedex.R
import com.jesil.pokedex.data.remote.responses.Pokemon
import kotlin.math.round

@Composable
fun PokemonWeightHeightSection(
    modifier: Modifier = Modifier,
    pokemonWeight: Int,
    pokemonHeight: Int,
    sectionHeight: Dp = 50.dp,
) {
    val pokemonWeightInKg = remember(pokemonWeight) {
        round(pokemonWeight * 100f) / 1000f
    }
    val pokemonHeightInMeters = remember(pokemonHeight) {
        round(pokemonHeight * 100f) / 1000f
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        content = {
            PokemonWeightHeightItem(
                modifier = Modifier.weight(1f),
                dataValue = pokemonWeightInKg,
                dataUnit = "KG",
                dataIcon = painterResource(id = R.drawable.ic_weight),
            )
            Spacer(
                modifier = Modifier
                    .size(1.dp, sectionHeight)
                    .background(MaterialTheme.colorScheme.onSurface)
            )
            PokemonWeightHeightItem(
                modifier = Modifier.weight(1f),
                dataValue = pokemonHeightInMeters,
                dataUnit = "M",
                dataIcon = painterResource(id = R.drawable.ic_height)
            )
        }
    )

}

@Composable
fun PokemonWeightHeightItem(
    modifier: Modifier = Modifier,
    dataValue: Float,
    dataUnit: String,
    dataIcon: Painter,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Icon(
                modifier = Modifier.size(25.dp),
                painter = dataIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$dataValue$dataUnit",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )
        }
    )

}