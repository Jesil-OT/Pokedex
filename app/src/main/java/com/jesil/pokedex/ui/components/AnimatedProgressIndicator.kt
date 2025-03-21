package com.jesil.pokedex.ui.components

import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.imageloading.rememberDrawablePainter
import com.jesil.pokedex.R

@Composable
fun AnimatedProgressIndicator(
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberDrawablePainter(
            drawable = getDrawable(
                LocalContext.current,
                R.drawable.loading_gif
            )
        ),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}