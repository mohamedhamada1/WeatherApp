package uae.weather.myapplication.ui.landing.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import uae.weather.myapplication.R
import uae.weather.myapplication.databinding.FragmentLandingBinding
import uae.weather.myapplication.ui.base.BaseFragment
import javax.inject.Inject


class FragmentLanding :
    BaseFragment<FragmentLandingBinding, ViewModel>() {
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    override val layoutId: Int
        get() = R.layout.fragment_landing

    override val viewModel: ViewModel
        get() = ViewModelProvider(
            this,
            mViewModelFactory
        ).get(ViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }


    private fun setupView() = viewDataBinding?.apply {
        btnByLocation.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.action_fragmentLanding_to_fragmentWeatherByLocation)
        }
        btnSearchAboutCity.setOnClickListener {
            Navigation.findNavController(view!!).navigate(R.id.action_fragmentLanding_to_fragmentList)
        }


    }


}