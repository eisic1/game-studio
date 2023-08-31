package ba.etf.rma23.data

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var title: String,
    @SerializedName("platform") var platform: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("rating") val rating: Double,
    @SerializedName("cover_image") val coverImage: String,
    @SerializedName("esrb_rating") val esrbRating: String,
    @SerializedName("developer") val developer: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("summary") val description: String,
    @SerializedName("user_impressions") val userImpressions: List<UserImpression>
){
   /* constructor(
        title: String,
        platformValues: List<Int>,
        releaseDate: String,
        rating: Double,
        coverImage: String,
        esrbRating: String,
        developer: String,
        publisher: String,
        genre: String,
        description: String,
        userImpressions: List<UserImpression>
    ) : this(
        title,
        "",
        releaseDate,
        rating,
        coverImage,
        esrbRating,
        developer,
        publisher,
        genre,
        description,
        userImpressions
    ) {
        // Koristimo CoroutineScope za izvršavanje asinkronog koda
        CoroutineScope(Dispatchers.Main).launch {
            val mappedPlatformValue = mapPlatformValue(platformValues)
            platform = mappedPlatformValue
        }
    }

    companion object {
        private val api = IGDBApiConfig.retrofit
        private suspend fun mapPlatformValue(platformValue: List<Int>): String {
            val fields = "fielsd id, name; where id = \"$platformValue[0]\""
            val requestBody = RequestBody.create(MediaType.parse("text/plain"), fields)
            val response = api.getPlatforms(requestBody)
            if(response.isSuccessful){
                val newPlatform = response.body()
                Log.d("OVO JE PROBA", newPlatform?.get(0).toString())
                return newPlatform?.get(0)?.name ?: ""
            }else{
                println("OVO JE PROBA, GREŠKA")
            }

            return ""
        }
    }*/
}
/*
val title: String,
val platform: String,
val releaseDate: String,
val rating: Double,
val coverImage: String,
val esrbRating: String,
val developer: String,
val publisher: String,
val genre: String,
val description: String,
val userImpressions: List<UserImpression>*/