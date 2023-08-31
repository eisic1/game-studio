package ba.etf.rma23.projekat.data.repositories

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RMAApi {
    // SPIRALA 3

    // Endpoint koji dohvata sve igre sa swagger-a
    @GET("account/{aid}/games")
    suspend fun getAllRMAGames(@Path("aid") aid: String): List<RMAGame>

    // Endpoint koji sprema igru na swagger
    @POST("account/{aid}/game")
    suspend fun setRMAGame(@Path("aid") aid: String, @Body game: GameRequest): RMAGame

    // Endpoint koji briše igru sa određenim id-jem sa swagger-a
    @DELETE("account/{aid}/game/{gid}")
    suspend fun removeRMAGame(@Path("aid") aid: String, @Path("gid") gid: Int): Any

    // Endpoint koji briše sve igre sa swagger-a
    @DELETE("account/{aid}/game")
    suspend fun removeRMAAllGame(@Path("aid") aid: String)


    // SPIRALA 4

    // Endpoint koji dohvata sve review-ove za određenu igru
    @GET("game/{gid}/gamereviews")
    suspend fun getReviewForGame(@Path("gid") gid: Int): List<GameReview>

    // Endpoint koji postavlja review za određenu igru
    @POST("account/{aid}/game/{gid}/gamereview")
    suspend fun setReviewGame(@Path("aid") aid: String, @Path("gid") gid: Int, @Body review: RequestBody): GameReview

    // Endpoint koji briše sve review-ove
    @DELETE("account/{aid}/gamereviews")
    suspend fun deleteAllReviews(@Path("aid") aid: String)
}