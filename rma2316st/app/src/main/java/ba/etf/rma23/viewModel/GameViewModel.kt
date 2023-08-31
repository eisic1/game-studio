package ba.etf.rma23.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ba.etf.rma23.data.Game

/* Klasa koja služi za čuvanje podataka u landscape orijentaciji
    Naslijeđena iz klase ViewModel, a ViewModel klasa upravo pomaže u čuvanje
    podataka prilikom okretanja ekrana
*/
class GameViewModel: ViewModel() {
    // MutableLiveData je podklasa od LiveData, a sadrži zapravo podatak koji se mijenja kroz komponente
    private val gameData = MutableLiveData<Game>()

    fun setGame(game: Game){
        gameData.value = game
    }

    fun getGame(): LiveData<Game>{
        return gameData;
    }
}