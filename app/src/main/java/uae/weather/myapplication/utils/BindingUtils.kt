package uae.weather.myapplication.utils

import android.view.View
import androidx.databinding.BindingAdapter

object BindingUtils {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}