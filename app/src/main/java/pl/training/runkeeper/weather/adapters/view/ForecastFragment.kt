package pl.training.runkeeper.weather.adapters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import dagger.hilt.android.AndroidEntryPoint
import pl.training.runkeeper.R
import pl.training.runkeeper.commons.ViewState
import pl.training.runkeeper.commons.ViewState.Failed
import pl.training.runkeeper.commons.ViewState.Initial
import pl.training.runkeeper.commons.ViewState.Loaded
import pl.training.runkeeper.commons.ViewState.Loading
import pl.training.runkeeper.commons.hideKeyboard
import pl.training.runkeeper.commons.setDrawable
import pl.training.runkeeper.databinding.FragmentForecastBinding

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private val viewModel: ForecastViewModel by activityViewModels()
    private val forecastRecyclerViewAdapter = ForecastRecyclerViewAdapter()

    private lateinit var binding: FragmentForecastBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentForecastBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        viewModel.viewState.observe(viewLifecycleOwner, ::updateView)
        viewModel.city.observe(viewLifecycleOwner, ::updateCity)
        binding.nextDaysForecastRecycler.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        binding.nextDaysForecastRecycler.adapter = forecastRecyclerViewAdapter
        binding.checkButton.setOnClickListener(::onForecastCheck)
        forecastRecyclerViewAdapter.tapListener = {
            viewModel.selectedDayForecast = it
            findNavController().navigate(R.id.show_forecast_details)
        }
    }

    private fun updateView(viewState: ViewState) = when (viewState) {
        is Initial -> rest()
        is Loading -> {
            rest()
            binding.descriptionText.text = getString(R.string.loading)
        }
        is Loaded<*> -> showForecast(viewState.get())
        is Failed -> {
            rest()
            Toast.makeText(requireContext(), viewState.message, LENGTH_SHORT).show()
        }
    }

    private fun updateCity(city: String) {
        binding.cityNameText.text = city
    }

    private fun rest() {
        binding.iconImage.setImageDrawable(null)
        binding.descriptionText.text = getString(R.string.empty)
        binding.temperatureText.text = getString(R.string.empty)
        binding.pressureText.text = getString(R.string.empty)
        forecastRecyclerViewAdapter.update(emptyList())
    }

    private fun showForecast(forecast: List<DayForecastViewModel>) {
        val currentForecast = forecast.first()
        binding.iconImage.setDrawable(currentForecast.iconName)
        binding.descriptionText.text = currentForecast.description
        binding.temperatureText.text = currentForecast.temperature
        binding.pressureText.text = currentForecast.pressure
        forecastRecyclerViewAdapter.update(forecast.drop(1))
    }

    private fun onForecastCheck(view: View) {
        val city = binding.cityNameEdit.text.toString()
        if (city.isNotEmpty()) {
            view.hideKeyboard()
            viewModel.refreshForecast(city)
        }
    }

}