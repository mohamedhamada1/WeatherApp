package uae.weather.myapplication.ui.location.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import uae.weather.myapplication.R
import uae.weather.myapplication.databinding.FragmentWeatherByLocationBinding

import uae.weather.myapplication.ui.base.BaseFragment

import uae.weather.myapplication.ui.location.models.WeatherByLocationInterface
import uae.weather.myapplication.ui.location.viewmodel.FragmentWeatherByLocationViewModel
import uae.weather.myapplication.utils.*
import javax.inject.Inject


class FragmentWeatherByLocation :
    BaseFragment<FragmentWeatherByLocationBinding, FragmentWeatherByLocationViewModel>() {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    override val layoutId: Int
        get() = R.layout.fragment_weather_by_location

    override val viewModel: FragmentWeatherByLocationViewModel
        get() = ViewModelProvider(
            this,
            mViewModelFactory
        ).get(FragmentWeatherByLocationViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        startGetLocation()
    }

    private fun startGetLocation() = runWithPermissions(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) {
        viewDataBinding?.resource = Resource(Status.LOADING, null, null)
        viewDataBinding?.TVLoadingTitle?.text = getString(R.string.gettingLocation)
        val gpsLocationManger = GpsLocationManger(this, REQUEST_GPS_REQUEST)
        gpsLocationManger.setGPSListener(object : GpsLocationManger.GPSListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                if (isGPSEnable)
                    gpsLocationManger.setUpLocationListener(context)
            }
            override fun updateLocation(lat: Double, lng: Double) {
                gpsLocationManger.clear()
                loadWeather(lat, lng)
            }
        })
        gpsLocationManger.turnGPSOn()
    }


    private fun setupView() = viewDataBinding?.apply {
        RVResult.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalSpaceItemDecorator(context, 10, 10))
            setHasFixedSize(true)
            adapter = WeatherByLocationAdapter(appExecutors)
        }
    }

    private fun loadWeather(lat: Double, lng: Double) {
        viewDataBinding?.TVLoadingTitle?.text = getString(R.string.loadTheData)
        viewModel.loadWeather(lat, lng).observe(viewLifecycleOwner,
            Observer<Resource<List<WeatherByLocationInterface>>> { response ->
                viewDataBinding?.resource = response
                response.let {
                    if (it.status == Status.ERROR) {
                        Toast.makeText(context, it.throwable?.message, Toast.LENGTH_SHORT).show()
                    }
                   else if (it.status == Status.SUCCESS) {
                        it.data.let {
                            viewDataBinding?.apply {
                                (RVResult.adapter as WeatherByLocationAdapter).submitList(it)
                            }
                        }
                    }

                }

            })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GPS_REQUEST) {
                startGetLocation()
            }
        } else {
            viewDataBinding?.TVLoadingTitle?.text = getString(R.string.You_dont_allow_location)
            viewDataBinding?.progressBar?.visibility = View.GONE
            Toast.makeText(context, getString(R.string.You_dont_allow_location), Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        const val REQUEST_GPS_REQUEST = 101
    }

}