package im.bernier.movies.genre

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(@PrimaryKey(autoGenerate = false) val id: Int, val name: String)