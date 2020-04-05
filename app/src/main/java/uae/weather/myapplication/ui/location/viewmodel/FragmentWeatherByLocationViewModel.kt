package uae.weather.myapplication.ui.location.viewmodel

import uae.weather.myapplication.ui.base.BaseViewModel
import uae.weather.myapplication.ui.list.datamodel.WeatherDataModel
import javax.inject.Inject
class FragmentWeatherByLocationViewModel @Inject constructor(val weatherDataMode: WeatherDataModel) :
    BaseViewModel() {
    fun loadWeather(lat:Double,lng:Double) = weatherDataMode.loadWeatherByLocation(lat,lng)

}





