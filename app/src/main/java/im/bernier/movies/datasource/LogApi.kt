package im.bernier.movies.datasource

import im.bernier.movies.analytics.LogItem
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Url

interface LogApi {
    @PUT
    fun putLog(
        @Url url: String,
        @Body logItem: LogItem,
    )
}
