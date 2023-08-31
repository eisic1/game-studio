package ba.etf.rma23.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.R
import ba.etf.rma23.data.UserImpression
import ba.etf.rma23.data.UserRating
import ba.etf.rma23.data.UserReview

class UserImpressionAdapter (
    private var impressions: List<UserImpression>
) : RecyclerView.Adapter<UserImpressionAdapter.ImpressionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImpressionViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_impression, parent, false)
        return ImpressionViewHolder(view)
    }

    override fun getItemCount(): Int = impressions.size

    override fun onBindViewHolder(holder: ImpressionViewHolder, position: Int) {
        holder.username.text = impressions[position].username;

        if (impressions[position] is UserReview){
            val newUserReview: UserReview = impressions[position] as UserReview
            holder.review.text = newUserReview.review
            holder.review.visibility = View.VISIBLE
            holder.rating_bar.visibility = View.GONE
        }
        if (impressions[position] is UserRating){
            val newUserRating: UserRating = impressions[position] as UserRating
            holder.rating_bar.numStars = 5
            holder.rating_bar.rating = newUserRating.rating.toFloat()
            holder.review.visibility = View.GONE
            holder.rating_bar.visibility = View.VISIBLE
        }

    }
    fun updateImpression(impressions: List<UserImpression>) {
        this.impressions = impressions.sortedByDescending { it.timestamp }
        notifyDataSetChanged()
    }
    inner class ImpressionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username_textview)
        val rating_bar: RatingBar = itemView.findViewById(R.id.rating_bar)
        val review: TextView = itemView.findViewById(R.id.review_textview)
    }
}