package com.jesil.pokedex.ui.pokemonlist

import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.imageloading.rememberDrawablePainter
import com.jesil.pokedex.R
import com.jesil.pokedex.ui.components.AnimatedProgressIndicator
import com.jesil.pokedex.ui.components.GoToTop
import com.jesil.pokedex.ui.components.PokedexEntry
import com.jesil.pokedex.ui.components.RetrySection
import com.jesil.pokedex.ui.components.SearchBar
import com.jesil.pokedex.ui.components.isScrollingUp
import com.jesil.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListScreen(
    navController: NavController
) {
    val viewModel: PokemonListViewModel = koinViewModel()
    val defaultDominantColor = MaterialTheme.colorScheme.surface

    val pokemonList by remember { viewModel.pokemonList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    val listState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        content = {
            Column {
                Spacer(Modifier.height(20.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(R.drawable.ic_international_pok_mon_logo),
                    contentDescription = stringResource(R.string.pokemon_logo),
                )

                if (!isLoading && loadError.isEmpty()) {
                    AnimatedVisibility(visible = listState.isScrollingUp(), enter = slideInVertically(), exit = slideOutVertically()){
                    Spacer(Modifier.height(20.dp))
                    val hintText = remember { mutableStateOf("") }
                    SearchBar(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        hint = hintText.value,
                        onSearch = {
                            hintText.value = it
                            viewModel.searchPokemonList(hintText.value)
                        }
                    )
                        }
                }

                Spacer(Modifier.height(16.dp))
                LazyVerticalGrid(
                    contentPadding = PaddingValues(16.dp),
                    state = listState,
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    content = {
                        itemsIndexed(pokemonList) { index, pokemon ->
                            if (index >= pokemonList.size - 1 && !endReached && !isLoading && !isSearching) {
                                viewModel.loadPokemonPaginated()
                            }
                            PokedexEntry(
                                entry = pokemon,
                                onClick = { dominantColor ->
                                    navController.navigate(
                                        "pokemon_detail_screen/${dominantColor.toArgb()}/${pokemon.pokemonName}/${pokemon.number}"
                                    )
                                },
                            )
                        }
                    }
                )

                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            AnimatedProgressIndicator(
                                modifier = Modifier.align(alignment = Alignment.Center)
                            )
                        }
                    )
                }
                if (loadError.isNotEmpty()) {
                    RetrySection(
                        error = loadError,
                        onRetry = {
                            viewModel.loadPokemonPaginated()
                        }
                    )
                }
            }
            AnimatedVisibility(visible = !listState.isScrollingUp(), enter = fadeIn(), exit = fadeOut()) {
                GoToTop(
                    modifier = Modifier.navigationBarsPadding()
                ) {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun PokemonListScreenPreview() {
    PokedexTheme {
        PokemonListScreen(navController = rememberNavController())
    }
}