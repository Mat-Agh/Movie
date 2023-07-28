package app.mat.movie.presentation.component.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.mat.movie.common.type.MovieWatchProviderType
import app.mat.movie.data.remote.dto.common.WatchProvidersDto
import app.mat.movie.presentation.component.button.MoviesWatchProvidersTypeButton
import app.mat.movie.presentation.component.chip.LogoChip
import app.mat.movie.presentation.component.text.SectionLabel
import app.mat.movie.presentation.theme.spacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WatchProvidersSection(
    title: String,
    watchProviders: WatchProvidersDto,
    modifier: Modifier = Modifier
) {
    val stream = watchProviders.flatRate?.sortedBy { provider ->
        provider.displayPriority
    } ?: emptyList()
    val rent = watchProviders.rent?.sortedBy { provider ->
        provider.displayPriority
    } ?: emptyList()
    val buy = watchProviders.buy?.sortedBy { provider ->
        provider.displayPriority
    } ?: emptyList()

    val availableTypes: List<MovieWatchProviderType> = buildList {
        if (stream.isNotEmpty()) {
            add(
                MovieWatchProviderType.Stream
            )
        }
        if (rent.isNotEmpty()) {
            add(
                MovieWatchProviderType.Rent
            )
        }
        if (buy.isNotEmpty()) {
            add(
                MovieWatchProviderType.Buy
            )
        }
    }
    var selectedType by remember(
        availableTypes
    ) {
        mutableStateOf(
            availableTypes.firstOrNull()
        )
    }

    val selectedProviders by remember(
        selectedType
    ) {
        mutableStateOf(
            when (selectedType) {
                MovieWatchProviderType.Stream -> stream
                MovieWatchProviderType.Buy -> buy
                MovieWatchProviderType.Rent -> rent
                else -> null
            }
        )
    }

    if (availableTypes.isNotEmpty()) {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.spacing.medium
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionLabel(
                    modifier = Modifier.weight(1f),
                    text = title
                )

                selectedType?.let { currentType ->
                    MoviesWatchProvidersTypeButton(
                        modifier = modifier,
                        selectedType = currentType,
                        availableTypes = availableTypes
                    ) { type ->
                        selectedType = type
                    }
                }
            }
            LazyRow(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(
                    MaterialTheme.spacing.small
                ),
                contentPadding = PaddingValues(
                    horizontal = MaterialTheme.spacing.medium
                )
            ) {
                selectedProviders?.let { providers ->
                    items(
                        providers.count()
                    ) { index ->
                        val provider = providers[index]
                        LogoChip(
                            modifier = Modifier.animateItemPlacement(),
                            logoPath = provider.logoPath,
                            text = provider.providerName
                        )
                    }
                }
            }
        }
    }
}