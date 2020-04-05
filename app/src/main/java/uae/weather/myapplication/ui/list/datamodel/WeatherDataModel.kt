package uae.weather.myapplication.ui.list.datamodel

import androidx.lifecycle.MutableLiveData
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uae.weather.myapplication.ui.list.models.City
import uae.weather.myapplication.ui.list.models.WeatherResponse
import uae.weather.myapplication.ui.location.models.WeatherByLocationInterface
import uae.weather.myapplication.ui.location.models.WeatherByLocationResponse
import uae.weather.myapplication.utils.APIEndPoint
import uae.weather.myapplication.utils.Resource
import uae.weather.myapplication.utils.getIds
import uae.weather.myapplication.utils.toModel
import javax.inject.Inject

class WeatherDataModel @Inject constructor(
) {

    fun loadWeatherByLocation(
        lat: Double,
        Lng: Double
    ): MutableLiveData<Resource<List<WeatherByLocationInterface>>> {
        val result = MutableLiveData<Resource<*>>()
        result.setValue(Resource.loading(null))
        Rx2AndroidNetworking.get(String.format(APIEndPoint.weatherForecastByLocationAPI, lat, Lng))
            .build()
            .getObjectObservable(WeatherByLocationResponse::class.java) // This returns you an Observable
            .subscribeOn(Schedulers.io()) // do the network call on another thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.setValue(Resource.success( it.list.toModel(), 1))
            }, {
                result.setValue(Resource.error<Any>(null, it, 1))
            })
        return result as MutableLiveData<Resource<List<WeatherByLocationInterface>>>
    }

    fun loadWeather(cities: List<City>): MutableLiveData<Resource<WeatherResponse>> {
        val result = MutableLiveData<Resource<*>>()
        result.setValue(Resource.loading(null))
        Rx2AndroidNetworking.get(String.format(APIEndPoint.weatherAPI, cities.getIds()))
            .build()
            .getObjectObservable(WeatherResponse::class.java) // This returns you an Observable
            .subscribeOn(Schedulers.io()) // do the network call on another thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                result.setValue(Resource.success(it, 1))
            }, {
                result.setValue(Resource.error<Any>(null, it, 1))
            })
        return result as MutableLiveData<Resource<WeatherResponse>>
    }

}