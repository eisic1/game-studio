package ba.etf.rma23.view

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.R
import ba.etf.rma23.data.Game

class GameListAdapter (
    private var games: List<Game>,
    private val onItemClicked: (game: Game) -> Unit
) : RecyclerView.Adapter<GameListAdapter.GameViewHolder>()  {

    private lateinit var activity: Activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        // Dohvatanje konteksta kako bi se koristio za sharedPreferences
        activity = parent.context as Activity
        return GameViewHolder(view)
    }
    override fun getItemCount(): Int = games.size
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.gameTitle.text = games[position].title;
        holder.gameRating.text = games[position].rating.toString();
        holder.releaseDate.text = games[position].releaseDate;
        holder.gamePlatform.text = games[position].platform;

        // Listener za click
        holder.itemView.setOnClickListener{
            onItemClicked(games[position])
            // Spremanje podataka kako bih znali koja je igra zadnja otvorena
            val sharedPreferences = activity?.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.putInt("lastGameIndex", position)?.apply()
        }
    }
    fun updateGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }
    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameTitle: TextView = itemView.findViewById(R.id.item_title_textview)
        val gameRating: TextView = itemView.findViewById(R.id.game_rating_textview)
        val releaseDate: TextView = itemView.findViewById(R.id.game_release_date_textview)
        val gamePlatform: TextView = itemView.findViewById(R.id.game_platform_textview)
    }
}