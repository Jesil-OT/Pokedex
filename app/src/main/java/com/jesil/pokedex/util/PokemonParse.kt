package com.jesil.pokedex.util

import androidx.compose.ui.graphics.Color
import com.jesil.pokedex.data.remote.responses.Stat
import com.jesil.pokedex.data.remote.responses.Type
import com.jesil.pokedex.ui.theme.AtkColor
import com.jesil.pokedex.ui.theme.DefColor
import com.jesil.pokedex.ui.theme.HPColor
import com.jesil.pokedex.ui.theme.SpAtkColor
import com.jesil.pokedex.ui.theme.SpDefColor
import com.jesil.pokedex.ui.theme.SpdColor
import com.jesil.pokedex.ui.theme.TypeBug
import com.jesil.pokedex.ui.theme.TypeDark
import com.jesil.pokedex.ui.theme.TypeDragon
import com.jesil.pokedex.ui.theme.TypeElectric
import com.jesil.pokedex.ui.theme.TypeFairy
import com.jesil.pokedex.ui.theme.TypeFighting
import com.jesil.pokedex.ui.theme.TypeFire
import com.jesil.pokedex.ui.theme.TypeFlying
import com.jesil.pokedex.ui.theme.TypeGhost
import com.jesil.pokedex.ui.theme.TypeGrass
import com.jesil.pokedex.ui.theme.TypeGround
import com.jesil.pokedex.ui.theme.TypeIce
import com.jesil.pokedex.ui.theme.TypeNormal
import com.jesil.pokedex.ui.theme.TypePoison
import com.jesil.pokedex.ui.theme.TypePsychic
import com.jesil.pokedex.ui.theme.TypeRock
import com.jesil.pokedex.ui.theme.TypeSteel
import com.jesil.pokedex.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: String): Color {
//    type.type.name.lowercase(Locale.ROOT)
    return when(type) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when(stat.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when(stat.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}