package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import ba.etf.rma23.data.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import java.lang.Exception

object GameReviewsRepository {
    private var hash: String = "c009b3a3-8a7a-4e17-a3ed-31f034ade978"
    private val api = AccountApiConfig.retrofit

    suspend fun getOfflineReviews(context: Context):List<GameReview>{
        return withContext(Dispatchers.IO){
            var db = AppDatabase.getInstance(context)
            /*val tmpBaza = db.gameReviewDao().getReviews()
            api.removeRMAAllGame(hash)
            api.deleteAllReviews(hash)*/
            var listReviews = db.gameReviewDao().getOfflineReviews()
            return@withContext listReviews
        }
    }

    suspend fun sendOfflineReviews(context: Context):Int{
        return withContext(Dispatchers.IO){
            var count = 0;
            var db = AppDatabase.getInstance(context)
            var listReviews = db.gameReviewDao().getOfflineReviews()
            for(rev in listReviews){
                //api.deleteAllReviews(hash)
                val test = sendReview(context, rev)
                if(test){
                    rev.online = true
                    db.gameReviewDao().updateReview(rev)
                    count++
                }
            }
            return@withContext count
        }

    }

    suspend fun sendReview(context: Context, gameReview: GameReview):Boolean{
        return withContext(Dispatchers.IO){
            try {
                var returnValue = false;
                var db = AppDatabase.getInstance(context)
                //val tmpBaza = db.gameReviewDao().getReviews()
                val favourite = AccountGamesRepository.getSavedGames()
                var favGame: Game? = null
                var test = false
                for (game in favourite) {
                    if (game.id === gameReview.igdb_id) {
                        favGame = game
                        test = true
                    }
                }
                if (!test) {
                    val newFavGame = GamesRepository.getGamesById(gameReview.igdb_id)
                    if(newFavGame.size !== 0) favGame = AccountGamesRepository.saveGame(newFavGame.get(0))
                }
                val json = """
                {
                    "review": "${gameReview.review}",
                    "rating": ${gameReview.rating}
                }
            """.trimIndent()
                val requestBody = RequestBody.create(MediaType.parse("application/json"), json)
                val result = api.setReviewGame(hash, favGame!!.id, requestBody)

                //var returnValue = false;
                if (result is GameReview) {
                    returnValue = true
                    result.online = true
                } else {
                    //var db = AppDatabase.getInstance(context)
                    db.gameReviewDao().insertReview(gameReview)
                    returnValue = false
                }
                return@withContext  returnValue
            }catch (e: Exception){
                var db = AppDatabase.getInstance(context)
                db.gameReviewDao().insertReview(gameReview)
                //returnValue = false
                return@withContext false
            }
        }
    }

    // pomoćna metoda koja testira da li se podaci dodaju u bazu
    suspend fun setReviewDatabase(gameReview: GameReview, context: Context): Boolean{
        return withContext(Dispatchers.IO){
            var db = AppDatabase.getInstance(context)
            db.gameReviewDao().insertReview(gameReview)
            return@withContext true
        }
    }

    suspend fun getReviewsForGame(igdb_id: Int):List<GameReview>{
        return withContext(Dispatchers.IO){
            try {
                var itemsReview = listOf<GameReview>()
                val result = api.getReviewForGame(igdb_id)
                if(result.size !== 0) itemsReview = result
                return@withContext itemsReview
            }catch (e: Exception){
                return@withContext emptyList()
            }

        }
    }
}