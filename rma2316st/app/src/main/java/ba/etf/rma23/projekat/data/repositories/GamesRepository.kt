package ba.etf.rma23.projekat.data.repositories

import android.util.Log
import ba.etf.rma23.data.Game
import ba.etf.rma23.data.GameData
import ba.etf.rma23.data.UserImpression
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody

object GamesRepository {
    private val api = IGDBApiConfig.retrofit

    suspend fun getGamesByName(name:String): List<Game>{
        return withContext(Dispatchers.IO){
            val listGames = mutableListOf<Game>()
            val fields = "fields id, name, platforms.name, release_dates.human, cover.url, genres.name, summary, involved_companies.company.name; limit 10;"
            val query = "search \"$name\";$fields"
            val requestBody = RequestBody.create(MediaType.parse("text/plain"), query)
            val responseGames = api.getGames(requestBody).body()
            if (responseGames != null) {
                for(game in responseGames){
                    listGames.add(setAttributesGame(game))
                }
            }
            return@withContext listGames
        }
    }

    // Metoda koja traži igru preko ID-ja, koristi se prilikom slanja review-a, ukoliko igra nije spremljena u omiljene
    suspend fun getGamesById(id:Int): List<Game>{
        return withContext(Dispatchers.IO){
            val listGames = mutableListOf<Game>()
            val fields = "fields id, name, platforms.name, release_dates.human, cover.url, genres.name, summary, involved_companies.company.name; limit 10;"
            val query = "$fields where id = $id;"
            val requestBody = RequestBody.create(MediaType.parse("text/plain"), query)
            val responseGames = api.getGames(requestBody).body()
            if (responseGames != null) {
                for(game in responseGames){
                    listGames.add(setAttributesGame(game))
                }
            }
            return@withContext listGames
        }
    }

    suspend fun getGamesSafe(name:String):List<Game>{
        return withContext(Dispatchers.IO){
            val listGames = mutableListOf<Game>()
            val userAge = AccountGamesRepository.getAge()
            if(userAge == null) return@withContext listGames
            val fields = "fields id, name, platforms.name, release_dates.human, cover.url, genres.name, summary, involved_companies.company.name; limit 10;"
            val query = "search \"$name\";$fields where age_ratings.rating = ${AccountGamesRepository.getAge()};"
            val requestBody = RequestBody.create(MediaType.parse("text/plain"), query)
            val responseGames = api.getGames(requestBody).body()
            if (responseGames != null) {
                for(game in responseGames){
                    listGames.add(setAttributesGame(game))
                }
            }
            return@withContext listGames
        }
    }

    // Metoda za sortiranje igara
    fun sortGames(): List<Game> {
        val listGames = GameData.getAll()
        val listFavourites = GameData.getFavouritesGames()
        val sortedFavourites = listFavourites.sortedBy { it.title.toLowerCase() }
        val sortedGames = listGames.sortedBy { it.title.toLowerCase() }
        //listGames.sortedWith(compareByDescending<Game> { it.favourite }.thenBy { it.title })
        return sortedFavourites.plus(sortedGames)
    }
    // Metoda koja dohvata prvih (bilo kojih) deset igara, za prikaz na početnom zaslonu
    suspend fun getAllGames(): List<Game> {
        return withContext(Dispatchers.IO) {
            val listGames = mutableListOf<Game>()
            val fields = "fields id, name, platforms.name, release_dates.human, cover.url, genres.name, summary, involved_companies.company.name; limit 10;"
            val requestBody = RequestBody.create(MediaType.parse("text/plain"), fields)
            val responseGames = api.getGames(requestBody).body()
            if (responseGames != null) {
                for(game in responseGames){
                    listGames.add(setAttributesGame(game))
                }
            }
            GameData.setGames(listGames)
            return@withContext listGames
        }
    }
    // Metoda koja služi za postavljanje stvarnih atributa Game klase
    private fun setAttributesGame(game: GamesResponse): Game {
        val gson = Gson()

        var platform = ""
        var rDate = ""
        var cover = ""
        var genres = ""
        var description = ""
        var developer = ""
        if(game.platforms !== null){
            val json = gson.toJson(game.platforms[0])
            platform = json.split("\"name\":")[1].split("\"")[1]
        }
        if(game.releaseDate !== null){
            val json = gson.toJson(game.releaseDate[0])
            rDate = json.split("\"human\":")[1].split("\"")[1]
        }
        if(game.cover !== null){
            val json = gson.toJson(game.cover)
            val jsonObjName = json.split("\"url\":")[1].split("\"")[1]
            cover = "https:$jsonObjName"
        }
        if(game.genres !== null){
            val json = gson.toJson(game.genres[0])
            genres = json.split("\"name\":")[1].split("\"")[1]
        }
        if(game.summary !== null) description = game.summary
        if(game.developer !== null){
            var counter = 0;
            for(dev in game.developer){
                val json = gson.toJson(game.developer[0])
                val jsonObjName = json.split("\"name\":")[1].split("\"")[1]
                if(counter < game.developer.size-1) developer += "$jsonObjName,";
                else developer += jsonObjName;
                counter++;
            }

        }

        return Game(game.id, game.name, platform, rDate, 0.0, cover, "", developer, developer, genres, description, listOf<UserImpression>())
    }
}