package im.bernier.movies.genre

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Genre(@PrimaryKey(autoGenerate = false) val id: Int, val name: String)