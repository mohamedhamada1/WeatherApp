package uae.weather.myapplication.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uae.weather.myapplication.ui.list.viewmodel.ListFragmentViewModel
import uae.weather.myapplication.ui.location.viewmodel.FragmentWeatherByLocationViewModel

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(ListFragmentViewModel::class)
    abstract fun bindListFragmentViewModel(listFragmentViewModel: ListFragmentViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(FragmentWeatherByLocationViewModel::class)
    abstract fun bindWeatherByLocationViewModel(WeatherByLocationViewModel: FragmentWeatherByLocationViewModel): ViewModel

}