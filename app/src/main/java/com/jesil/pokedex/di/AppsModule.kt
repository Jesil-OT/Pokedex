package com.jesil.pokedex.di

import com.jesil.pokedex.data.remote.PokeApi
import com.jesil.pokedex.repository.PokemonRepo
import com.jesil.pokedex.ui.pokemonlist.PokemonListViewModel
import com.jesil.pokedex.util.Constants.BASE_URL
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single<PokeApi>{
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    single { PokemonRepo(get()) }

    viewModelOf(::PokemonListViewModel) bind PokemonListViewModel::class

}