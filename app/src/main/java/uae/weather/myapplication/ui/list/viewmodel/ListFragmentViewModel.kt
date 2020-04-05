package uae.weather.myapplication.ui.list.viewmodel

import uae.weather.myapplication.ui.base.BaseViewModel
import uae.weather.myapplication.ui.list.datamodel.WeatherDataModel
import uae.weather.myapplication.ui.list.models.City
import javax.inject.Inject

//   val homeRepository: HomeRepository,
class ListFragmentViewModel @Inject constructor(val weatherDataMode: WeatherDataModel) :
    BaseViewModel() {
    var cities: MutableList<City>? = null
    var selectedCities: MutableList<City> = arrayListOf<City>()
    fun addCity(city: City?) = city?.let {
        if (!selectedCities.contains(city))
            selectedCities.add(city)
    }


    fun removeCity(city: City) = selectedCities.remove(city)

    fun isAllowedToLoad() = selectedCities.size in 3..7
    fun loadWeather() = weatherDataMode.loadWeather(selectedCities)

}





