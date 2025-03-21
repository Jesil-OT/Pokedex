package com.jesil.pokedex.ui.pokemondetail

import androidx.lifecycle.ViewModel
import com.jesil.pokedex.data.remote.responses.Pokemon
import com.jesil.pokedex.repository.PokemonRepo
import com.jesil.pokedex.util.Resource

class PokemonDetailViewModel(
    private val repository: PokemonRepo
): ViewModel(){

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }

}