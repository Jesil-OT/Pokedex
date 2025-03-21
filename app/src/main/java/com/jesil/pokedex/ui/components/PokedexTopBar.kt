package com.jesil.pokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.pokedex.R
import com.jesil.pokedex.ui.theme.PokedexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexTopBar(
    modifier: Modifier = Modifier,
    pokemonId: String,
    backgroundColor: Color,
    onNavigationIconClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            IconButton(
                onClick = { onNavigationIconClick() },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_two),
                        contentDescription = stringResource(R.string.go_back),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                text = stringResource(id = R.string.app_name),
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp
            )

            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "#${pokemonId}",
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp
            )
        }
    )

}

@PreviewLightDark
@Composable
private fun PokedexTopBarPreview() {
    PokedexTheme {
        PokedexTopBar(
            backgroundColor = MaterialTheme.colorScheme.surface,
            pokemonId = "#001",
        ) {}
    }
}