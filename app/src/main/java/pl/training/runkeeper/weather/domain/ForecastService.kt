package pl.training.runkeeper.weather.domain

import pl.training.runkeeper.weather.ports.Forecast
import pl.training.runkeeper.weather.ports.ForecastProvider
import pl.training.runkeeper.weather.ports.RefreshForecastFailedException

class ForecastService(private val forecastProvider: ForecastProvider): Forecast {

    override suspend fun getForecast(city: String) = try {
        forecastProvider.getForecast(city)
    } catch (exception: Exception) {
        throw RefreshForecastFailedException()
    }

}