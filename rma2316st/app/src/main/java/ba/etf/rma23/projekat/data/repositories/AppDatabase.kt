package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ba.etf.rma23.projekat.data.repositories.GameReview

@Database(entities = arrayOf(GameReview::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameReviewDao(): GameReviewDAO
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "game-db"
            ).build()
    }
}