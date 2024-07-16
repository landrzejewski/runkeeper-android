package pl.training.runkeeper.weather.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.runkeeper.commons.ViewState
import pl.training.runkeeper.commons.ViewState.Failed
import pl.training.runkeeper.commons.ViewState.Initial
import pl.training.runkeeper.commons.ViewState.Loaded
import pl.training.runkeeper.commons.ViewState.Loading
import pl.training.runkeeper.commons.formatDate
import pl.training.runkeeper.commons.formatPressure
import pl.training.runkeeper.commons.formatTemperature
import pl.training.runkeeper.weather.Container
import pl.training.runkeeper.weather.domain.DayForecast
import pl.training.runkeeper.weather.ports.RefreshForecastFailedException

class ForecastViewModel : ViewModel() {

    private val forecastService = Container.forecastService()
    private val state = MutableLiveData<ViewState<List<DayForecastViewModel>>>(Initial())

    val viewState: LiveData<ViewState<List<DayForecastViewModel>>> = state

    fun refreshForecast(city: String) {
        viewModelScope.launch {
            state.postValue(Loading())
            try {
                val data = forecastService.getForecast(city)
                    .map(::toViewModel)
                state.postValue(Loaded(data))
            } catch (exception: RefreshForecastFailedException) {
                state.postValue(Failed("Forecast refresh failed"))
            }
        }
    }

    private fun toViewModel(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastViewModel(formatDate(data), formatTemperature(temperature), formatPressure(pressure), description, iconName)
    }

}