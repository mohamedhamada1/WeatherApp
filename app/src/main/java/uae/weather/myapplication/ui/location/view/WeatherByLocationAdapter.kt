package uae.weather.myapplication.ui.location.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import uae.weather.myapplication.R

import uae.weather.myapplication.databinding.RowWeatherByLocationBinding
import uae.weather.myapplication.databinding.RowWeatherHeaderBinding
import uae.weather.myapplication.ui.base.common.DataBoundListAdapter

import uae.weather.myapplication.ui.location.models.WeatherByLocation
import uae.weather.myapplication.ui.location.models.WeatherByLocationHeader
import uae.weather.myapplication.ui.location.models.WeatherByLocationInterface
import uae.weather.myapplication.utils.AppExecutors

class WeatherByLocationAdapter(
    appExecutors: AppExecutors
) : DataBoundListAdapter<WeatherByLocationInterface, ViewDataBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<WeatherByLocationInterface>() {
        override fun areItemsTheSame(
            oldItem: WeatherByLocationInterface,
            newItem: WeatherByLocationInterface
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: WeatherByLocationInterface,
            newItem: WeatherByLocationInterface
        ): Boolean {
            return false
        }
    }
) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        if (viewType == VIEW_HEADER) {
            return DataBindingUtil
                .inflate<RowWeatherHeaderBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.row_weather_header,
                    parent,
                    false
                )
        } else {
            return DataBindingUtil
                .inflate<RowWeatherByLocationBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.row_weather_by_location,
                    parent,
                    false
                )
        }


    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position) is WeatherByLocationHeader)
            return 1
        return super.getItemViewType(position)
    }

    override fun bind(binding: ViewDataBinding, item: WeatherByLocationInterface) {
        if (item is WeatherByLocationHeader)
            (binding as RowWeatherHeaderBinding).header = item
        else if (item is WeatherByLocation)
            (binding as RowWeatherByLocationBinding).item = item

    }

    companion object {
        const val VIEW_HEADER = 1
        const val VIEW_ITEM = 2
    }
}