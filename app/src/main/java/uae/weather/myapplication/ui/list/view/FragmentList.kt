package uae.weather.myapplication.ui.list.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.*
import uae.weather.myapplication.R
import uae.weather.myapplication.databinding.FragmentListBinding
import uae.weather.myapplication.ui.base.BaseFragment
import uae.weather.myapplication.ui.list.models.City
import uae.weather.myapplication.ui.list.models.WeatherResponse
import uae.weather.myapplication.ui.list.viewmodel.ListFragmentViewModel
import uae.weather.myapplication.utils.*
import javax.inject.Inject


class FragmentList : BaseFragment<FragmentListBinding, ListFragmentViewModel>() {


    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    // simple implementation for coroutine to just load the cities file which has names and ids
    var job = SupervisorJob()
    val uiScope = CoroutineScope(Dispatchers.Main + job)
    val bgDispatcher: CoroutineDispatcher = Dispatchers.IO

    override val layoutId: Int
        get() = R.layout.fragment_list

    override val viewModel: ListFragmentViewModel
        get() = ViewModelProvider(this, mViewModelFactory).get(ListFragmentViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.btnSearch?.isEnabled = viewModel.isAllowedToLoad()
        uiScope.launch {
            withContext(bgDispatcher) {
                // background thread
                loadAssets()
            }
            setupView() //UI
        }
    }

     fun loadAssets() {
        // this should be load in bg because this is long operation
        viewModel.cities = loadAssets("city.list.json")?.parseAs<MutableList<City>>()
    }

    private fun setupView() = viewDataBinding?.apply {
        cityAutocomplete.apply {
            threshold = 1
            setAdapter(viewModel.cities?.let {
                ArrayAdapter(
                    context,
                    android.R.layout.simple_expandable_list_item_1, it
                )
            }).also {
                setOnItemClickListener { adapterView, _, i, _ ->
                    val city = adapterView.getItemAtPosition(i) as? City
                    viewModel.addCity(city)
                    RVSelectedCity.adapter?.notifyDataSetChanged()
                    cityAutocomplete.setText("")
                    btnSearch.isEnabled = viewModel.isAllowedToLoad()
                }
            }
        }
        RVSelectedCity.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
            addItemDecoration(
                HorizontalSpacesItemDecoration(
                    context.dp2Px(10).toInt(),
                    context.dp2Px(10).toInt()
                )
            )
            setHasFixedSize(true)
            adapter = SelectedCityAdapter(
                appExecutors
            ) { item ->
                viewModel.removeCity(item)
                RVSelectedCity.adapter?.notifyDataSetChanged()
                btnSearch.isEnabled = viewModel.isAllowedToLoad()
            }.also {
                it.submitList(viewModel.selectedCities)
            }
        }
        btnSearch.setOnClickListener {
            loadWeather()
            activity?.hideKeybord()
        }
        RVResult.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalSpaceItemDecorator(context, 10, 10))
            setHasFixedSize(true)
            adapter = WeatherAdapter(appExecutors)
        }


    }
    
    private fun loadWeather() {
        viewModel.loadWeather().observe(viewLifecycleOwner,
            Observer<Resource<WeatherResponse>> { response ->
                response.let {
                    if (it.status == Status.ERROR) {
                        Toast.makeText(context, it.throwable?.message, Toast.LENGTH_SHORT).show()
                    } else if (it.status == Status.SUCCESS) {
                        it.data.let {
                            viewDataBinding?.apply {
                                (RVResult.adapter as WeatherAdapter).submitList(it?.list)
                            }
                        }
                    }

                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        uiScope.coroutineContext.cancel()
    }

}