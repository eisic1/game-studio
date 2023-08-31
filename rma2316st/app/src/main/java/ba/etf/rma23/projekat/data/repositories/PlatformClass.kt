package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class PlatformClass(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)