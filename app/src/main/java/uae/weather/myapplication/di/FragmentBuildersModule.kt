package uae.weather.myapplication.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import uae.weather.myapplication.ui.landing.view.FragmentLanding
import uae.weather.myapplication.ui.list.view.FragmentList
import uae.weather.myapplication.ui.location.view.FragmentWeatherByLocation


@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFragmentList(): FragmentList

    @ContributesAndroidInjector
    abstract fun contributeFragmentWeatherByLocation(): FragmentWeatherByLocation

    @ContributesAndroidInjector
    abstract fun contributeFragmentLanding(): FragmentLanding
}