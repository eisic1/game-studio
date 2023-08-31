package ba.etf.rma23.view

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.Margines
import ba.etf.rma23.R
import ba.etf.rma23.data.Game
import ba.etf.rma23.data.GameData
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.rma23.viewModel.GameViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment: Fragment() {
    private lateinit var gamesList: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter
    private lateinit var searchButton: Button
    private lateinit var searchQuery: EditText
    private lateinit var sortButton: Button
    private lateinit var favouritesButton: Button
    private lateinit var allGameList: List<Game>
    private lateinit var gameViewModel: GameViewModel
    private lateinit var bottomNavigation: BottomNavigationView
    private val scope = CoroutineScope(Dispatchers.Main)
    //private var allGameList = GameData.getAll()
    //private var lastGame =  Game("","","", 0.0, "", "", "", "", "", "", listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("---", "")
        // Inicijalizacija ViewModel-a
        gameViewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
        //val scope = CoroutineScope(Job() + Dispatchers.Main)
        allGameList = GameData.getAll()
            scope.launch {
                try {
                    /*AccountGamesRepository.saveGame(Game(24273,"Age of Empires: The Age of Kings","","",10.0,"","","","","","",listOf<UserImpression>()))
                    //AccountGamesRepository.removeAllGame()
                    context?.let {
                        GameReviewsRepository.sendReview(GameReview(3, 5, null, 24273),
                            it
                        )
                    }

                    //GameReviewsRepository.getReviewsForGame(24273)

                    context?.let { GameReviewsRepository.getOfflineReviews(it) }*/


                    /*context?.let {
                        GameReviewsRepository.setReviewDatabase(
                            GameReview(2345, 5, null, 24273, false),
                            it
                        )
                    }*/


                    val result = GamesRepository.getAllGames()
                    //val proba = AccountGamesRepository.removeGame(3073)
                    GameData.setGames(result)
                    allGameList = GameData.getAll()
                    gamesAdapter.updateGames(allGameList)
                } catch (e: Exception) {
                    println("ERROR" + e)
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_home, container, false)
        /*val navigationItem = bottomNavigation.menu.findItem(R.id.details_button)
        navigationItem.isEnabled = true*/

        searchButton = view.findViewById(R.id.search_button)
        searchQuery = view.findViewById(R.id.search_query_edittext)
        sortButton = view.findViewById(R.id.sort_button)
        favouritesButton = view.findViewById(R.id.fav_button)
        gamesList = view.findViewById(R.id.game_list)
        gamesList.layoutManager = GridLayoutManager(activity, 2)
        gamesAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
        gamesList.adapter = gamesAdapter
        gamesAdapter.updateGames(allGameList)

        searchButton.setOnClickListener {
            allGameList = listOf<Game>()
            scope.launch {
                try {
                    AccountGamesRepository.setAge(18)
                    if(AccountGamesRepository.getAge() >= 18)
                        allGameList = GamesRepository.getGamesByName(searchQuery.text.toString())
                    else allGameList = GamesRepository.getGamesSafe(searchQuery.text.toString())
                    GameData.setGames(allGameList)
                    gamesAdapter.updateGames(allGameList)
                } catch (e: Exception) {
                    println("ERROR" + e)
                }
            }
        }

        sortButton.setOnClickListener {
            val sortedGames = GamesRepository.sortGames();
            GameData.setGames(sortedGames)
            gamesAdapter.updateGames(sortedGames)
        }
        favouritesButton.setOnClickListener {
            scope.launch {
                val result = AccountGamesRepository.getSavedGames()
                GameData.setGames(result)
                val favouritesGame = GameData.getFavouritesGames();
                gamesAdapter.updateGames(favouritesGame)
            }

        }

        gamesList.addItemDecoration(Margines(10))
        return view;
    }
    private fun showGameDetails(game: Game) {

        // Provjera orijentacije
        val orientation = resources.configuration.orientation
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            // Ukoliko se nalazimo u landscape, čuvamo kliknutu igru u ViewModel-u
            gameViewModel.setGame(game)
        }else{
            // U suprotnom postavljamo instancu fragmenta o detaljima i
            // mijenjamo ga sa trenutnim
            val fragmentDetails = GameDetailsFragment.newInstance()

            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragmentDetails)
            transaction?.addToBackStack(null)
            transaction?.commit()

            // Kada se uradi klik na igru, dohvati se ID od BottomNavigation iz glavne aktivnosti i setuje se na TRUE
            bottomNavigation = requireActivity().findViewById(R.id.bottom_nav)
            bottomNavigation.menu.findItem(R.id.details_button).isEnabled = true
        }
    }
    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}
