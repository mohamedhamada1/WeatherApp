package uae.weather.myapplication.ui.list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import uae.weather.myapplication.R
import uae.weather.myapplication.databinding.RowSelectedCityBinding
import uae.weather.myapplication.ui.base.common.DataBoundListAdapter
import uae.weather.myapplication.ui.list.models.City
import uae.weather.myapplication.utils.AppExecutors

class SelectedCityAdapter(
    appExecutors: AppExecutors,
    private val callback: ((City) -> Unit)?
) : DataBoundListAdapter<City, RowSelectedCityBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }
    }
) {

    override fun createBinding(parent: ViewGroup, viewType:Int): RowSelectedCityBinding {
        val binding = DataBindingUtil
            .inflate<RowSelectedCityBinding>(
                LayoutInflater.from(parent.context),
                R.layout.row_selected_city,
                parent,
                false
            )
        binding.IVRemove.setOnClickListener {
            binding.city?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: RowSelectedCityBinding, item: City) {
        binding.city = item
    }

}