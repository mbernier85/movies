package im.bernier.movies.feature.discover.datasource

import androidx.paging.PagingState

fun getRefreshKeyAny(state: PagingState<Int, out Any>): Int? {
    val anchorPosition: Int = state.anchorPosition ?: return null

    val (_, prevKey, nextKey) =
        state.closestPageToPosition(anchorPosition)
            ?: return null

    if (prevKey != null) {
        return prevKey + 1
    }

    return if (nextKey != null) {
        nextKey - 1
    } else {
        null
    }
}
