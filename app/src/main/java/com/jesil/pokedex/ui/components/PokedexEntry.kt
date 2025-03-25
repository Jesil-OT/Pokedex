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
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.SuccessResult
import coil3.request.allowHardware
import com.google.accompanist.coil.rememberCoilPainter
import com.jesil.pokedex.data.models.PokedexListEntry
import timber.log.Timber

@Composable
fun PokedexEntry(
    modifier: Modifier = Modifier,
    entry: PokedexListEntry,
    onClick: () -> Unit,
    dominantColor: Color,
    configDominantColor: (Drawable) -> Unit,
    onDominantColorLoaded: (Color) -> Unit = {}
) {
    val imageLoading = remember { mutableStateOf(false) }
    val dominantColor2 = dominantColorFromUrl(entry.imageUrl)

    Box(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(dominantColor2)
            .clickable { onClick() },
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

                AsyncImage(
                    model = entry.imageUrl,
                    contentDescription = "Loaded Image",
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                )


//                Image(
//                    modifier = Modifier
//                        .size(120.dp)
//                        .align(Alignment.CenterHorizontally),
//                    painter = painter,
//                    contentDescription = entry.pokemonName,
//                    contentScale = ContentScale.Crop,
////                    onState = {
////                        if (it is AsyncImagePainter.State.Loading) {
////                            imageLoading.value = true
////                        } else if (it is AsyncImagePainter.State.Success) {
////                            imageLoading.value = false
////                        }
////                    }
//                )
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

@Composable
fun dominantColorFromUrl(
    url: String,
): Color {
    val context = LocalContext.current
    var dominantColor by remember { mutableStateOf(Color.Gray) } // Default color

    LaunchedEffect(url) {
        val imageLoader = coil.ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false) // Necessary to get a Drawable
            .build()

        val result = imageLoader.execute(request)
        if (result is coil.request.SuccessResult) {
            val bitmap = (result.drawable as? BitmapDrawable)?.bitmap?.copy(Bitmap.Config.ARGB_8888, true)
            bitmap?.let {
                Palette.from(it).generate(){ palette ->
                    palette?.dominantSwatch?.rgb?.let { colorValue ->
                        dominantColor = Color(colorValue)
                    }
                }
//                val extractedColor = palette.getDominantColor(0xFF888888.toInt()) // Default gray
//                onDominantColorLoaded(Color(extractedColor))
//                dominantColor = Color(extractedColor)
            }
        } else {
            Timber.e("Failed to load drawable from URL: $url")
        }
    }

    return dominantColor
}