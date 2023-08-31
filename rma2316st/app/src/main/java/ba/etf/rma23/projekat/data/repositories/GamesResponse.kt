package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.data.UserImpression
import com.google.gson.annotations.SerializedName

data class GamesResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("platforms") val platforms: List<Any>,
    @SerializedName("release_dates") val releaseDate: List<Any>,
    @SerializedName("rating") val rating: Double,
    @SerializedName("cover") val cover: Any,
    @SerializedName("esrb_rating") val esrbRating: String,
    @SerializedName("involved_companies") val developer: List<Any>,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("genres") val genres: List<Any>,
    @SerializedName("summary") val summary: String,
    @SerializedName("user_impressions") val userImpressions: List<UserImpression>
)