package uae.weather.myapplication.ui.list.models

import java.text.NumberFormat


data class WeatherResponse(val cnt:Int,val list:List<WeatherItem>)
data class WeatherItem(val name:String, val wind: Wind, val main: Main, val weather:List<Weather> ){
    fun getMinTemp():String{
      return  NumberFormat.getNumberInstance().format(main.temp_min)
    }
    fun getMaxTemp():String{
        return  NumberFormat.getNumberInstance().format(main.temp_max)
    }
    fun getDesc():String{
        weather[0].let {
           return  it.description

        }
    }
}
data class Wind(val speed:Float,val deg:Int)
data class Main(val temp:Float,val temp_min:Float,val temp_max:Float,val pressure:Float ,val ss:Float = 2.1f)
data class Weather(val id:Long,val main:String,val description:String)

