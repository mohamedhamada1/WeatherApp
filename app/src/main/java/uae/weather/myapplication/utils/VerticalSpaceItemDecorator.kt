package uae.weather.myapplication.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import uae.weather.myapplication.utils.dp2Px


class VerticalSpaceItemDecorator(
    context: Context,
    topSpaceDP: Int,
    bottomSpaceDP: Int = topSpaceDP
) : RecyclerView.ItemDecoration() {

    private val topSpacePX = context.dp2Px(topSpaceDP).toInt()
    private val bottomSpacePX = context.dp2Px(bottomSpaceDP).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = topSpacePX
        outRect.bottom = bottomSpacePX
        outRect.left=topSpacePX
        outRect.right = topSpacePX
    }
}