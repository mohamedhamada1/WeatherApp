package uae.weather.myapplication.ui.location.models

import uae.weather.myapplication.ui.list.models.Main
import uae.weather.myapplication.ui.list.models.Weather
import uae.weather.myapplication.ui.list.models.Wind
import uae.weather.myapplication.utils.formatToString
import java.text.NumberFormat
import java.util.*


/** for adapter will create list that has header and list */
interface WeatherByLocationInterface

data class WeatherByLocationList(val list: List<WeatherByLocationInterface>)
data class WeatherByLocationHeader(var date: String, var dt_Date: Date) :
    WeatherByLocationInterface {
    fun getDateInFormat() = dt_Date.formatToString(DATE_FORMAT)
    companion object {
        const val DATE_FORMAT = "EE , MMM dd, yyyy"

    }
}


data class WeatherByLocationResponse(val cnt: Int, val list: List<WeatherByLocation>)
data class WeatherByLocation(
    val dt_txt: String,
    var dt_Date: Date,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
) : WeatherByLocationInterface {
    fun getMinTemp(): String {
        return NumberFormat.getNumberInstance().format(main.temp_min)
    }

    fun getMaxTemp(): String {
        return NumberFormat.getNumberInstance().format(main.temp_max)
    }

    fun getDesc(): String {
        weather[0].let {
            return it.description

        }
    }

    fun getTimeOfInfo() = dt_Date.formatToString(HOUR_FORMAT)

    companion object {
        const val API_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        const val HOUR_FORMAT = "hh:mm a"
    }
}