package com.jesil.pokedex.data.remote.responses

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)