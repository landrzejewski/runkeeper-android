package pl.training.runkeeper.weather.domain

import pl.training.runkeeper.weather.ports.ForecastProvider

class ForecastService(private val forecastProvider: ForecastProvider) {

    suspend fun getForecast(city: String) = forecastProvider.getForecast(city)

}