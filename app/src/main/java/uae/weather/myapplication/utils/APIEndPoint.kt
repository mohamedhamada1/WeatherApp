package uae.weather.myapplication.utils

import uae.weather.myapplication.BuildConfig


class APIEndPoint {

    companion object{
        const val weatherAPI= BuildConfig.BASE_URL+"group?id=%s&units=metric&appid="+BuildConfig.API_KEY
        const val weatherForecastByLocationAPI= BuildConfig.BASE_URL+"forecast?lat=%s&lon=%s&appid="+BuildConfig.API_KEY

    }

}