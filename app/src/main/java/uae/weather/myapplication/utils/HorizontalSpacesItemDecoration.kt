package uae.weather.myapplication.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class HorizontalSpacesItemDecoration(private val edgeSpace: Int, private val betweenSpace: Int) :
    ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == 0) { // first item
            outRect.left = edgeSpace
        } else if (parent.getChildAdapterPosition(view) == state.itemCount - 1) { // last item
            outRect.right = edgeSpace
            outRect.left = betweenSpace
        } else {
            outRect.left = betweenSpace
        }
    }

}