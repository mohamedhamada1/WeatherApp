package uae.weather.myapplication.utils

import uae.weather.myapplication.ui.location.models.WeatherByLocation
import uae.weather.myapplication.ui.location.models.WeatherByLocationHeader
import uae.weather.myapplication.ui.location.models.WeatherByLocationInterface

fun List<WeatherByLocation>.toModel(): List<WeatherByLocationInterface> {

    val weatherList = arrayListOf<WeatherByLocationInterface>()
    forEachIndexed { index, weatherByLocation ->
        weatherByLocation.dt_Date =
            weatherByLocation.dt_txt.parseDate(WeatherByLocation.API_DATE_FORMAT)
        if (index == 0) {
            // first item , so we have to add header
            weatherList.add(WeatherByLocationHeader(weatherByLocation.dt_txt,weatherByLocation.dt_Date))
            weatherList.add(weatherByLocation)
        } else {
            // check for each next item if has new date or not
            if (weatherByLocation.dt_Date.isNewDay(this.get(index - 1).dt_Date) ) {
                // this is new header
                weatherList.add(WeatherByLocationHeader(weatherByLocation.dt_txt,weatherByLocation.dt_Date))
                weatherList.add(weatherByLocation)
            } else {
                // just add new item
                weatherList.add(weatherByLocation)
            }

        }

    }
    return weatherList

}