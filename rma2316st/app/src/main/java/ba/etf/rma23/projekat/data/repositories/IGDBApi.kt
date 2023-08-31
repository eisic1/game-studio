package ba.etf.rma23.projekat.data.repositories

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IGDBApi {
    // Endpoint koji služi za dohvatanje svih igara, ili po specifikaciji u body
    @Headers(
        "Client-ID: e4gu68m79zgd0z415px3uhmdemqj43",
        "Authorization: Bearer 7ofllugnvhzftrv0ld6jo3e7vxavy8"
    )
    @POST("games")
    suspend fun getGames(@Body query: RequestBody): Response<List<GamesResponse>>
}