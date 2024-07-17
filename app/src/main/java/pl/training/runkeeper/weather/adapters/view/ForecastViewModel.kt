package pl.training.runkeeper.weather.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.training.runkeeper.commons.ViewState
import pl.training.runkeeper.commons.ViewState.Failed
import pl.training.runkeeper.commons.ViewState.Initial
import pl.training.runkeeper.commons.ViewState.Loaded
import pl.training.runkeeper.commons.ViewState.Loading
import pl.training.runkeeper.commons.formatDate
import pl.training.runkeeper.commons.formatPressure
import pl.training.runkeeper.commons.formatTemperature
import pl.training.runkeeper.commons.store.Store
import pl.training.runkeeper.weather.domain.DayForecast
import pl.training.runkeeper.weather.ports.Forecast
import pl.training.runkeeper.weather.ports.RefreshForecastFailedException
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastService: Forecast,
    private val store: Store
) : ViewModel() {

    private val state = MutableLiveData<ViewState>(Initial)
    private val cityName = MutableLiveData("")

    val viewState: LiveData<ViewState> = state
    val city: LiveData<String> = cityName
    var selectedDayForecast: DayForecastViewModel? = null

    init {
        val city = store.get(CITY_KEY)
        cityName.postValue(city)
        viewModelScope.launch {
            if (city.isNotEmpty()) {
                onDataLoaded(forecastService.getCachedForecast(city))
            }
        }
    }

    fun refreshForecast(city: String) {
        viewModelScope.launch {
            state.postValue(Loading)
            try {
                onDataLoaded(forecastService.getForecast(city))
                cityName.postValue(city)
                store.set(CITY_KEY, city)
            } catch (exception: RefreshForecastFailedException) {
                state.postValue(Failed("Forecast refresh failed"))
            }
        }
    }

    private fun onDataLoaded(data: List<DayForecast>) {
        val newState = Loaded(data.map(::toViewModel))
        state.postValue(newState)
    }

    private fun toViewModel(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastViewModel(formatDate(date), formatTemperature(temperature), formatPressure(pressure), description, iconName)
    }

    companion object {

        const val CITY_KEY = "city"

    }

}