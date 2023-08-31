package ba.etf.rma23.projekat.data.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GameReview(
    //@PrimaryKey @SerializedName("id") var id: Int = 0,
    @ColumnInfo(name = "rating") @SerializedName("rating") val rating: Int?,
    @ColumnInfo(name = "review") @SerializedName("review") val review: String?,
    @ColumnInfo(name = "igdb_id") @SerializedName("igdb_id") val igdb_id: Int,
    @ColumnInfo(name = "online") @SerializedName("online") var online: Boolean = false,
    @ColumnInfo(name = "student") @SerializedName("student") val student: String,
    @ColumnInfo(name = "timestamp") @SerializedName("timestamp") val timestamp: String,
    @PrimaryKey @SerializedName("id") var id: Int = 0,
)