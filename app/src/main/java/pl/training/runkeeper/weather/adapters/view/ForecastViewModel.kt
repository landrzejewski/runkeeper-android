package pl.training.runkeeper.weather.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.training.runkeeper.weather.adapters.provider.FakeForecastProvider
import pl.training.runkeeper.weather.domain.ForecastService
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.runkeeper.commons.formatDate
import pl.training.runkeeper.commons.formatPressure
import pl.training.runkeeper.commons.formatTemperature
import pl.training.runkeeper.weather.domain.DayForecast
import pl.training.runkeeper.weather.ports.Forecast

class ForecastViewModel : ViewModel() {

    private val forecastService: Forecast = ForecastService(FakeForecastProvider())
    private val forecastData = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = forecastData

    fun refreshForecast(city: String) {
        viewModelScope.launch {
            val data = forecastService.getForecast(city)
                .map(::toViewModel)
            forecastData.postValue(data)
        }
    }

    private fun toViewModel(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastViewModel(formatDate(data), formatTemperature(temperature), formatPressure(pressure), description, iconName)
    }

}