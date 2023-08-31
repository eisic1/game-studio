package ba.etf.rma23

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.PositionAssertions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma23.data.GameData
import ba.etf.rma23.projekat.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {
    fun hasItemCount(n: Int) = object : ViewAssertion {
        override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            assertTrue("View nije tipa RecyclerView", view is RecyclerView)
            var rv: RecyclerView = view as RecyclerView
            assertThat(
                "GetItemCount RecyclerView broj elementa: ",
                rv.adapter?.itemCount,
                Is(n)
            )
        }
    }

    @get:Rule
    var homeRule:ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun myTestOne(){
        /* Test koji provjerava da li display sadrži elemente sa odgovarajućim ID-jevima,
        za to se koristi funkcija matches u kombinaciji sa isDisplayed. Testiramo da li je prikazan BottomNavigation
        sa svojim item-ima, kao i search edittext sa dugmetom, te kontejner za prikaz fragmenata
        */
        onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))
        onView(withId(R.id.home_button)).check(matches(isDisplayed()))
        onView(withId(R.id.details_button)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(matches(isDisplayed()))
        onView(withId(R.id.search_query_edittext)).check(matches(isDisplayed()))
    }

    @Test
    fun myTestTwo(){
        /* Test koji provjerava stanje BottomNavigation item-a.
        Provjeravamo početno stanje, da li je Details button onemogućen. Nakon toga, kreiramo klik na prvu igru u listi,
        te ponovo provjeravamo stanje Details button-a, tj. da je ovaj put omogućen.
        */
        onView(withId(R.id.bottom_nav)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.details_button), isDisplayed(), isNotEnabled())).check(matches(isDisplayed()))
        var prvaIgra = GameData.getAll().get(0)
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.actionOnItem<ViewHolder>(allOf(
            hasDescendant(withText(prvaIgra.title)),
            hasDescendant(withText(prvaIgra.releaseDate)),
            hasDescendant(withText(prvaIgra.rating.toString()))
        ),click()))
        onView(allOf(withId(R.id.details_button), isDisplayed(), isEnabled())).check(matches(isDisplayed()))
    }
}