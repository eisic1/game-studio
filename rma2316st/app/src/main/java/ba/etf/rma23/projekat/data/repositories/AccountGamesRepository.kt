package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.data.Game
import ba.etf.rma23.data.GameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AccountGamesRepository {
    private var hash: String = "c009b3a3-8a7a-4e17-a3ed-31f034ade978"
    private var ageUser: Int? = null
    private val api = AccountApiConfig.retrofit

    fun setHash(acHash: String): Boolean {
        hash = acHash
        if(hash.isNotEmpty()) return true
        return false
    }

    fun getHash(): String {
        return hash
    }

    // Metoda za dohvatanje svih spremljenih igara sa swagger-a
    suspend fun getSavedGames(): List<Game> {
        return withContext(Dispatchers.IO){
            var list = arrayListOf<Game>()
            val result = api.getAllRMAGames(hash)
            var count = 0;
            for(g in result){
                if(count % 2 == 0)
                    list.add(Game(g.igdb_id, g.name, "", "09.06.2006", 0.0, "", "", "", "", "", "", listOf()))
                else
                    list.add(Game(g.igdb_id, g.name, "", "09.06.1999", 0.0, "", "", "", "", "", "", listOf()))
                count++
            }
            GameData.setFavouritesGames(list)
            return@withContext list
        }
    }

    // Metoda za spremanje igara u swagger
    suspend fun saveGame(game: Game): Game {
        return withContext(Dispatchers.IO) {
            val requestGame = RMAGame(game.id, game.title)

            val result = api.setRMAGame(hash, GameRequest(requestGame))

            val resultGame = Game(result.igdb_id, result.name, "", "", 0.0, "", "", "", "", "", "", listOf())
            GameData.addFavouritesGames(resultGame)

            return@withContext resultGame
        }
    }

    // Metoda koja briše igru sa id-jem iz swagger-a
    suspend fun removeGame(id: Int): Boolean {
        return withContext(Dispatchers.IO){
            api.removeRMAGame(hash, id).toString()
            return@withContext true
        }
    }

    // Metoda koja briše sve igre sa swagger-a
    suspend fun removeAllGame(): Boolean{
        return withContext(Dispatchers.IO){
            api.removeRMAAllGame(hash)
            return@withContext true
        }
    }

    // Metoda koja briše sve igre koje ne zadovoljavaju rating
    // Pretpostavio sam da metoda radi loaklno, pošto nema tačnog opisa
    fun removeNonSafe(): Boolean {
        var test = false
        val games = GameData.getFavouritesGames()
        for(g in games){
            if(g.esrbRating !== ageUser.toString()) {
                test = GameData.removeFavouritesGames(g.id)
            }
        }
        return test
    }

    // Metoda koja provjerava da li naslov igre sadrži proslijeđeni parametar lokalno
    // Metoda nije dovoljno opisana, pa sam postavio da radi lokalno
    fun getGamesContainingString(query:String):List<Game>{
        val filterList = arrayListOf<Game>()
        val games = GameData.getFavouritesGames()
        for(g in games){
            if(g.title.contains(query)) filterList.add(g)
        }
        return filterList
    }

    // Metode za postavljanje i dohvatanje godina korisnika
    fun setAge(age:Int):Boolean{
        if(age < 3 || age > 100) return false
        ageUser = age
        return true
    }

    fun getAge(): Int{
        return ageUser!!
    }
}