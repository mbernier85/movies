package im.bernier.movies.feature.tv

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet
import im.bernier.movies.R
import im.bernier.movies.navigation.EntryProviderInstaller
import im.bernier.movies.navigation.Navigator
import im.bernier.movies.util.imageUrl
import kotlinx.serialization.Serializable

@Serializable
data class ShowRoute(
    val id: Long,
)

@Module
@InstallIn(ActivityRetainedComponent::class)
object ShowModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller(navigator: Navigator): EntryProviderInstaller =
        {
            entry<ShowRoute> {
                TvShowScreen(
                    viewModel =
                        hiltViewModel(
                            creationCallback = { factory: TvShowViewModel.ShowViewModelFactory ->
                                factory.create(it.id)
                            },
                        ),
                )
            }
        }
}

@Composable
fun TvShowScreen(viewModel: TvShowViewModel) {
    val tvShow = viewModel.tvShow
    TvShowView(tvShow = tvShow)
}

@Composable
private fun TvShowView(tvShow: TV) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.size(120.dp, 180.dp),
                model = tvShow.poster_path?.imageUrl(),
                contentDescription = stringResource(id = R.string.tv_show_poster),
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = tvShow.name,
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = tvShow.overview,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TvShowScreenPreview() {
    TvShowView(
        tvShow =
            TV(
                name = "name",
            ),
    )
}
