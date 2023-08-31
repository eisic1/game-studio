package ba.etf.rma23.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.Margines
import ba.etf.rma23.R
import ba.etf.rma23.data.Game
import ba.etf.rma23.data.GameData
import ba.etf.rma23.data.UserImpression
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.viewModel.GameViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameDetailsFragment: Fragment() {
    private lateinit var game: Game
    private lateinit var gameTitle: TextView
    private lateinit var imageGame: ImageView
    private lateinit var platformGame: TextView
    private lateinit var releaseDateGame: TextView
    private lateinit var ratingGame: TextView
    private lateinit var developerGame: TextView
    private lateinit var publisherGame: TextView
    private lateinit var genreGame: TextView
    private lateinit var descriptionGame: TextView
    private lateinit var gamesList: RecyclerView
    private lateinit var impressionAdapter: UserImpressionAdapter
    private lateinit var addButton: Button
    private lateinit var deleteButton: Button
    private var userImpressionList = listOf<UserImpression>()
    private lateinit var gameViewModel: GameViewModel
    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicijalizacija ViewModel-a
        gameViewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_game_detail, container, false)

        gameTitle = view.findViewById(R.id.item_title_textview)
        imageGame = view.findViewById(R.id.cover_imageview)
        platformGame = view.findViewById(R.id.platform_textview)
        releaseDateGame = view.findViewById(R.id.release_date_textview)
        ratingGame = view.findViewById(R.id.esrb_rating_textview)
        developerGame = view.findViewById(R.id.developer_textview)
        publisherGame = view.findViewById(R.id.publisher_textview)
        genreGame = view.findViewById(R.id.genre_textview)
        descriptionGame = view.findViewById(R.id.description_textview)
        gamesList = view.findViewById(R.id.game_list)
        addButton = view.findViewById(R.id.add_button)
        deleteButton = view.findViewById(R.id.delete_button)

        // Citanje podataka iz pohrane kako bi prikazali posljednju igru
        val sharedPreferences = activity?.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
        val lastGameIndex = sharedPreferences?.getInt("lastGameIndex", -1)

        // Provjera da li igra postoji i njena obrada
        if (lastGameIndex != null && lastGameIndex > -1) {
            // Ako igra postoji, ovaj dio automatski važi i za portrait orijentaciju
            val allGameList = GameData.getAll()
            game = allGameList[lastGameIndex]
            populateDetails()
        }else{
            // U slučaju landscape orijentacije, čitamo podatke iz ViewModel-a
            // Dohvatimo spremljenu igru, i sa metodom "observe" dodajemo "Observe" kako bi obavijestili o promjenama
            gameViewModel.getGame().observe(viewLifecycleOwner, Observer { game ->
                this.game = game
                populateDetails()
            })
        }

        // Prikaz UserImpression-a
        gamesList.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        impressionAdapter = UserImpressionAdapter(listOf())
        gamesList.adapter = impressionAdapter
        impressionAdapter.updateImpression(userImpressionList)

        addButton.setOnClickListener {
            addGameToFavourites()
        }
        deleteButton.setOnClickListener {
            deleteGameToFavourites()
        }

        gamesList.addItemDecoration(Margines(10))

        return view
    }
    // Metoda za dodavanje trenutne igre u omiljene
    private fun addGameToFavourites() {
        val sharedPreferences = activity?.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
        val lastGameIndex = sharedPreferences?.getInt("lastGameIndex", -1)

        // Provjera da li igra postoji i njena obrada
        if (lastGameIndex != null && lastGameIndex > -1) {
            // Ako igra postoji, ovaj dio automatski važi i za portrait orijentaciju
            scope.launch {
                val allGameList = GameData.getAll()
                AccountGamesRepository.saveGame(allGameList[lastGameIndex])
            }

            //GameData.addFavouritesGames(allGameList[lastGameIndex])
            //GameData.setGames(allGameList)
        }
    }
    // Metoda za brisanje trenutne igre iz omiljenih
    private fun deleteGameToFavourites() {
        val sharedPreferences = activity?.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
        val lastGameIndex = sharedPreferences?.getInt("lastGameIndex", -1)

        // Provjera da li igra postoji i njena obrada
        if (lastGameIndex != null && lastGameIndex > -1) {
            // Ako igra postoji, ovaj dio automatski važi i za portrait orijentaciju
            scope.launch {
                val allGameList = GameData.getAll()
                AccountGamesRepository.removeGame(allGameList[lastGameIndex].id)
            }/*
            val allGameList = GameData.getFavouritesGames()
            GameData.removeFavouritesGames(allGameList[lastGameIndex].id)*/
            //GameData.setGames(allGameList)
        }
    }

    companion object {
        fun newInstance(): GameDetailsFragment = GameDetailsFragment()
    }

    private fun populateDetails() {
        // Postavljanje podataka na Layout
        gameTitle.text = game.title
        platformGame.text = game.platform
        releaseDateGame.text = game.releaseDate
        ratingGame.text = game.rating.toString()
        developerGame.text = game.developer
        publisherGame.text = game.publisher
        genreGame.text = game.genre
        descriptionGame.text = game.description
        userImpressionList = game.userImpressions

        val context: Context = imageGame.context
        var id: Int = context.resources.getIdentifier(game.coverImage, "drawable", context.packageName)

        // Na ovaj način prikazujem sliku za datu igricu koja se dohvata sa URL-a
        Glide.with(this).load(game.coverImage).into(imageGame)
    }
}