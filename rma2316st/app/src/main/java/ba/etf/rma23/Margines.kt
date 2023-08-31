package ba.etf.rma23

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Margines (
    private val space: Int,
    private val counter: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect){
            if(orientation == GridLayoutManager.VERTICAL){
                if(parent.getChildAdapterPosition(view) < counter) {
                    top = space
                }
                if(parent.getChildAdapterPosition(view) % counter == 0){
                    left = space
                }
            } else {
                if(parent.getChildAdapterPosition(view) < counter) {
                    left = space
                }
                if(parent.getChildAdapterPosition(view) % counter == 0){
                    top = space
                }
            }

            right = space
            bottom = space
        }
    }
}