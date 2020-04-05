package uae.weather.myapplication.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uae.weather.myapplication.ui.list.models.City
import java.io.InputStream
import java.lang.StringBuilder
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

fun Fragment.loadAssets(assetFileName: String): String? {
    val manager = resources.getAssets()
    val file: InputStream
    try {
        file = manager.open(assetFileName)
        val formArray = ByteArray(file.available())
        file.read(formArray)
        file.close()

        return String(formArray)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }

}


inline fun <reified T> typeOf(): Type =
    object : TypeToken<T>() {
    }.type

inline fun <reified T> String.parseAs(): T? =
    try {
        Gson().fromJson(this, typeOf<T>())
    } catch (t: Throwable) {
        t.printStackTrace()
        null
    }

fun Context.dp2Px(dp: Int): Float {
    val displayMetrics = resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics)
}

fun List<City>.getIds(): String {
    val ids = StringBuilder()
    forEachIndexed { index, city ->
        ids.append(city.id)
        if (index < size - 1) ids.append(",")
    }
    return ids.toString()
}

fun String.parseDate(format: String, defaultTimestamp: Long = 0): Date =
    try {
        SimpleDateFormat(format, Locale.US).parse(this) ?: Date(defaultTimestamp)
    } catch (t: Throwable) {
        Date(defaultTimestamp)
    }

fun Date.formatToString(format: String): String? =
    try {
        SimpleDateFormat(format, Locale.US).format(this)
    } catch (t: Throwable) {
        null
    }

fun Date.daysBetween(other: Date): Long =
    TimeUnit.DAYS.convert(abs(time - other.time), TimeUnit.MILLISECONDS)

fun Date.isNewDay(other: Date) =
    abs((this.formatToString("dd")?.toInt() ?: 0) - (other.formatToString("dd")?.toInt() ?: 0)) > 0


fun Activity.hideKeybord() {
    val view: View? = getCurrentFocus()
    if (view != null) {
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
