package ba.etf.rma23.projekat

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import ba.etf.rma23.R
import ba.etf.rma23.data.GameData
import ba.etf.rma23.view.GameDetailsFragment
import ba.etf.rma23.view.HomeFragment
import ba.etf.rma23.viewModel.GameViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Resetovanje posljednje igre koja je otvorena
        val sharedPreferences = this?.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putInt("lastGameIndex", -1)?.apply()

        val orientation = resources.configuration.orientation
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            // Aplikacija u Landscape orijentaciji
            setContentView(R.layout.activity_main_land)

            /* SELEKTOVANJE PRVE IGRE
                Za landscape sam bio pokušao da čuvam podatke kao u komentaru ispod,
                međutim, ovo mi nije mijenjalo detalje kada sam uradio klik na list.
                Istražio sam koji načini su još mogući,
                kao npr. da se proslijedi instanci fragmenta argument, gdje se koristi metoda "putParcelable",
                što mi nije radilo. Ono što radi jeste korištenje ViewModel klase
             */
            //val sharedPreferences = this?.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
            //val lastGame = sharedPreferences?.getInt("lastGameIndex", -1)
            //if(lastGame == -1) sharedPreferences?.edit()?.putInt("lastGameIndex", 0)?.apply()

            /* Inicijalizacija ViewModel-a */
            gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
            gameViewModel.setGame(GameData.getAll()[0])

            // Postavljanje fragmenata u landscape
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentHome, HomeFragment.newInstance())
                .add(R.id.fragmentDetails, GameDetailsFragment.newInstance())
                .commit()
        } else{
            // Aplikacija u portrait orijentaciji
            setContentView(R.layout.activity_main)

            bottomNavigation = findViewById(R.id.bottom_nav)
            // Resetovanje posljednje igre koja je otvorena
            /*val sharedPreferences = this?.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.putInt("lastGameIndex", -1)?.apply()*/
            val lastGame = sharedPreferences?.getInt("lastGameIndex", -1)

            bottomNavigation.menu.findItem(R.id.details_button).isEnabled = lastGame != -1

            // Rad sa navigacijom
            bottomNavigation.menu.findItem(R.id.logo_image).isEnabled = false
            bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
            bottomNavigation.selectedItemId = R.id.home_button
        }
    }

    //Listener za klik na navigaciji
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home_button -> {
                val fragmentHome = HomeFragment.newInstance()
                openFragment(fragmentHome)
                return@OnNavigationItemSelectedListener true
            }
            R.id.details_button -> {
                val fragmentDetails = GameDetailsFragment.newInstance()
                openFragment(fragmentDetails)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    //Funkcija za izmjenu fragmenta
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}