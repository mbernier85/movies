package im.bernier.movies.cast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CastScreen(viewModel: CastViewModel) {
    val person by viewModel.person.observeAsState()
    Column {
        GlideImage(
            model = person?.profile_path,
            contentDescription = person?.name,
            modifier = Modifier
                .size(120.dp, 180.dp)
                .clip(CircleShape)
                .padding(16.dp)
        )
        Text(
            text = person?.name ?: "",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(16.dp)
        )
        Text(
            text = person?.biography ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}