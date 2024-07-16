package pl.training.runkeeper.weather.domain

import pl.training.runkeeper.weather.ports.Forecast
import pl.training.runkeeper.weather.ports.ForecastProvider

class ForecastService(private val forecastProvider: ForecastProvider): Forecast {

    override suspend fun getForecast(city: String) = forecastProvider.getForecast(city)

}