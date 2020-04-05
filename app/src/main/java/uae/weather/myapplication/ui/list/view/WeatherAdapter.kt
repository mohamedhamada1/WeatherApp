package uae.weather.myapplication.ui.list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import uae.weather.myapplication.R
import uae.weather.myapplication.databinding.RowWeatherBinding
import uae.weather.myapplication.ui.base.common.DataBoundListAdapter
import uae.weather.myapplication.ui.list.models.WeatherItem
import uae.weather.myapplication.utils.AppExecutors

class WeatherAdapter(
    appExecutors: AppExecutors
) : DataBoundListAdapter<WeatherItem, RowWeatherBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<WeatherItem>() {
        override fun areItemsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: WeatherItem, newItem: WeatherItem): Boolean {
            return false
        }
    }
) {

    override fun createBinding(parent: ViewGroup, viewType:Int): RowWeatherBinding {
        return DataBindingUtil
            .inflate<RowWeatherBinding>(
                LayoutInflater.from(parent.context),
                R.layout.row_weather,
                parent,
                false
            )

    }

    override fun bind(binding: RowWeatherBinding, item: WeatherItem) {
        binding.item = item
    }

}