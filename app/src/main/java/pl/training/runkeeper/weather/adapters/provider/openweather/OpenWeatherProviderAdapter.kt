package pl.training.runkeeper.weather.adapters.provider.openweather

import android.util.Log
import pl.training.runkeeper.weather.domain.DayForecast
import pl.training.runkeeper.weather.ports.ForecastProvider

class OpenWeatherProviderAdapter(
    private val openWeatherApi: OpenWeatherApi,
    private val mapper: OpenWeatherProviderMapper
) : ForecastProvider {

    private val tag = OpenWeatherProviderAdapter::class.java.canonicalName

    override suspend fun getForecast(city: String): List<DayForecast> = try {
        openWeatherApi.getForecast(city)
            .forecast
            .map(mapper::toModel)
    } catch (exception: Exception) {
        Log.w(tag, "Fetching weather failed: " + exception.message)
        emptyList()
    }

}