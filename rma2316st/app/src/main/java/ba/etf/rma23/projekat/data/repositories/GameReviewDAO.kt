package ba.etf.rma23.projekat.data.repositories

import androidx.room.*

@Dao
interface GameReviewDAO {
    // Poziv na bazu, koji treba da vrati sve review-ove čiji je atribut online = false(0)
    @Query("SELECT * FROM gamereview WHERE online = 0")
    suspend fun getOfflineReviews(): List<GameReview>

    // Poziv na bazu, koji briše sve iz tabele "gamereview"
    @Query("DELETE FROM gamereview")
    suspend fun getReviews()

    // Poziv na bazu, koji upisuje podatak u bazu
    // onConflict = OnConflictStrategy.REPLACE - služi za izbjegavanje duplikata u bazi
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: GameReview)

    // Poziv na bazu, koji ažurira proslijeđeni review, koristi se za potrebe metode sendOfflineReviews
    @Update
    suspend fun updateReview(review: GameReview)
}