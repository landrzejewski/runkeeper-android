package pl.training.runkeeper.weather.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.training.runkeeper.weather.adapters.provider.FakeForecastProvider
import pl.training.runkeeper.weather.domain.ForecastService

class ForecastViewModel : ViewModel() {

    private val forecastService = ForecastService(FakeForecastProvider())
    private val forecastData = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = forecastData

}