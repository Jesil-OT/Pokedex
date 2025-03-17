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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.request.ImageRequest
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.google.accompanist.coil.rememberCoilPainter
import com.jesil.pokedex.data.models.PokedexListEntry

@Composable
fun PokedexEntry(
    modifier: Modifier = Modifier,
    entry: PokedexListEntry,
    onClick: (PokedexListEntry) -> Unit,
    dominantColor: Color,
    configDominantColor: (Drawable) -> Unit
) {
    val imageLoading = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(dominantColor)
            .clickable { onClick(entry) },
        contentAlignment = Alignment.Center,
        content = {
            Column {

//                CoilImage(
//                    request = ImageRequest.Builder(LocalContext.current)
//                        .data(entry.imageUrl)
//                        .target { configDominantColor(it) }
//                        .build(),
//                    contentDescription = entry.pokemonName,
//                    fadeIn = true,
//                    modifier = Modifier
//                        .size(120.dp)
//                        .align(Alignment.CenterHorizontally)
//                ){
//                    CircularProgressIndicator(
//                        color = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.align(Alignment.Center).scale(0.5f)
//                    )
//                }
//                AsyncImage(
//                    modifier = Modifier
//                        .size(120.dp)
//                        .align(Alignment.CenterHorizontally),
//                    model = rememberCoilPainter(
//                        request = ImageRequest.Builder(LocalContext.current)
//                            .data(entry.imageUrl)
//                            .target { configDominantColor(it) }
//                            .build(),
//                        fadeIn = true,
//                        requestBuilder = {
//                            crossfade(true)
//                        }
//                    ),
//                    contentDescription = entry.pokemonName,
//                    onState = {
//                        if (it is AsyncImagePainter.State.Loading) {
////                            configDominantColor(it.result.drawable)
//                            imageLoading.value = true
//                        } else if (it is AsyncImagePainter.State.Success) {
//                            imageLoading.value = false
//                        }
//                    }
//                )
                val request = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .target { configDominantColor(it) }
                    .build()

                AsyncImage(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally),
                    model = entry.imageUrl,
                    contentDescription = entry.pokemonName,
                    contentScale = ContentScale.Crop,
                    onState = {
                        if (it is AsyncImagePainter.State.Loading) {
                            imageLoading.value = true
                        } else if (it is AsyncImagePainter.State.Success) {
                            imageLoading.value = false
                        }
                    }
                )
                if (imageLoading.value) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.background,
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