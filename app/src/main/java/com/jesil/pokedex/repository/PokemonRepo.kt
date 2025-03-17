package com.jesil.pokedex.repository

import com.jesil.pokedex.data.remote.PokeApi
import com.jesil.pokedex.data.remote.responses.Pokemon
import com.jesil.pokedex.data.remote.responses.PokemonList
import com.jesil.pokedex.util.Resource

class PokemonRepo(
    private val api: PokeApi
) {
    suspend fun getPokemonList(pageSize: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(pageSize, offset)
        } catch (e: Exception) {
            return Resource.Failure("An unknown error occurred.")
        }

        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Failure("An unknown error occurred.")
        }

        return Resource.Success(response)
    }
}