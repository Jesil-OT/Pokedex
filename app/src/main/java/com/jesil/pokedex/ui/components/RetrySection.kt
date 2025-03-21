package com.jesil.pokedex.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jesil.pokedex.R

@Composable
fun RetrySection(
    modifier: Modifier = Modifier,
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            NoNetworkAnimationPreloader(
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = error,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.White
                ),
                onClick = { onRetry() },
                content = { Text(text = "Retry") }
            )
        }
    )
}

@Composable
fun DetailedRetrySection(
    error: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            NoNetworkAnimationPreloader(
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = error,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelSmall
            )
        }
    )
}

@Composable
fun NoNetworkAnimationPreloader(
    modifier: Modifier = Modifier,
) {
    val preLoaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.no_network)
    )

    val preLoaderProgress by animateLottieCompositionAsState(
        preLoaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
    )

    LottieAnimation(
        composition = preLoaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}