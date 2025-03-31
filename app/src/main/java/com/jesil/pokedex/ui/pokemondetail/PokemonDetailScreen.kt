package com.jesil.pokedex.ui.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.request.ImageRequest
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jesil.pokedex.R
import com.jesil.pokedex.data.remote.responses.Pokemon
import com.jesil.pokedex.ui.components.AnimatedProgressIndicator
import com.jesil.pokedex.ui.components.DetailedRetrySection
import com.jesil.pokedex.ui.components.PokedexTopBar
import com.jesil.pokedex.ui.components.PokemonBaseStats
import com.jesil.pokedex.ui.components.PokemonTypeSection
import com.jesil.pokedex.ui.components.PokemonWeightHeightSection
import com.jesil.pokedex.ui.components.RetrySection
import com.jesil.pokedex.ui.theme.PokedexTheme
import com.jesil.pokedex.util.Resource
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.util.Locale

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonName: String,
    pokemonNumber: Int,
    navController: NavController,
    pokemonImageSize: Dp = 200.dp
) {
//    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$pokemonNumber.png"
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonNumber.png"
    val viewModel: PokemonDetailViewModel = koinViewModel()
    val pokemonInfo by produceState<Resource<Pokemon>>(
        initialValue = Resource.Loading(),
        producer = {
            value = viewModel.getPokemonInfo(pokemonName)
        }
    )
    val imageLoading = remember { mutableStateOf(false) }
    // for dummy reasons
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = dominantColor)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        content = {
            Scaffold(
                topBar = {
                    Column(
                        modifier = Modifier.statusBarsPadding(),
                    ) {
                        PokedexTopBar(
                            pokemonId = pokemonNumber.toString(),
                            backgroundColor = dominantColor,
                            onNavigationIconClick = { navController.popBackStack() }
                        )
                        if (pokemonInfo is Resource.Success) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = 50.dp,
                                            bottomEnd = 50.dp
                                        )
                                    )
                                    .background(dominantColor)
                                ,
                                contentAlignment = Alignment.Center,
                                content = {
                                    if (imageLoading.value) {
                                        AnimatedProgressIndicator(
                                            modifier = Modifier
                                                .scale(0.5f)
                                        )
                                    }
                                    AsyncImage(
                                        modifier = Modifier
                                            .size(pokemonImageSize),
                                        model = imageUrl,
                                        contentDescription = pokemonName,
                                        contentScale = ContentScale.Crop,
                                        onState = {
                                            if (it is AsyncImagePainter.State.Loading) {
                                                imageLoading.value = true
                                            } else if (it is AsyncImagePainter.State.Success) {
                                                imageLoading.value = false
                                            }
                                        }
                                    )
                                }
                            )
                        }
                    }
                },
                content = { innerPadding ->
                    if (pokemonInfo is Resource.Loading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            content = {
                                AnimatedProgressIndicator(
                                    modifier = Modifier.align(alignment = Alignment.Center)
                                )
                            }
                        )
                    }
                    if (pokemonInfo is Resource.Success) {
                        Column(
                            modifier = Modifier
                                .consumeWindowInsets(innerPadding)
                                .padding(innerPadding)
                                .verticalScroll(rememberScrollState()),
                            content = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 20.dp),
                                    textAlign = TextAlign.Center,
                                    text = pokemonInfo.data?.name?.uppercase(Locale.getDefault())
                                        ?: "",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                PokemonTypeSection(
                                    modifier = Modifier.padding(horizontal = 30.dp),
                                    types = pokemonInfo.data?.types?.map {
                                        it.type.name.lowercase(Locale.ROOT)
                                    } ?: emptyList()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                PokemonWeightHeightSection(
                                    pokemonWeight = pokemonInfo.data?.weight ?: 0,
                                    pokemonHeight = pokemonInfo.data?.height ?: 0
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    text = stringResource(R.string.base_stats),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                pokemonInfo.data?.let {
                                    PokemonBaseStats(
                                        modifier = Modifier.padding(horizontal = 30.dp),
                                        pokemonInfo = it,
                                        animDelayPerItem = 100
                                    )
                                }
                            }
                        )
                    }
                    if (pokemonInfo is Resource.Failure) {
                        DetailedRetrySection(
                            error = pokemonInfo.message ?: "",
                        )
                    }
                }
            )
        }
    )
}

@PreviewLightDark
@Composable
private fun PokemonDetailScreenPreview() {
    PokedexTheme {
        PokemonDetailScreen(
            dominantColor = Color.White,
            pokemonName = "",
            navController = rememberNavController(),
            pokemonNumber = 100
        )
    }
}