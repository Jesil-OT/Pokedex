package com.jesil.pokedex.ui.components

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.request.ImageRequest
import coil.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil.request.SuccessResult
import coil3.request.allowHardware
import com.google.accompanist.coil.rememberCoilPainter
import com.jesil.pokedex.data.models.PokedexListEntry
import com.jesil.pokedex.ui.pokemonlist.PokemonListViewModel
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun PokedexEntry(
    modifier: Modifier = Modifier,
    entry: PokedexListEntry,
    onClick: (Color) -> Unit,
    viewModel : PokemonListViewModel = koinViewModel(),
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember { mutableStateOf(defaultDominantColor) }

    val imageLoading = remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(entry.imageUrl)
            .allowHardware(false) // Necessary to get a Drawable
            .build()

        val result = imageLoader.execute(request)
        if (result is SuccessResult) {
            viewModel.calcSolidDominantColor(
                result.drawable,
                onFinished = { color ->
                    dominantColor = color
                }
            )
        }
    }
    Box(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(dominantColor)
            .clickable { onClick(dominantColor) },
        contentAlignment = Alignment.Center,
        content = {
            Column {
                AsyncImage(
                    model = entry.imageUrl,
                    contentDescription = "Loaded Image",
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally),
                    onState = {
                        if (it is AsyncImagePainter.State.Loading) {
                            imageLoading.value = true
                        } else if (it is AsyncImagePainter.State.Success) {
                            imageLoading.value = false
                        }
                    }
                )

                if (imageLoading.value) {
                    AnimatedProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .scale(0.5f)
                    )
                }
                Text(
                    text = entry.pokemonName,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}
