package pl.training.runkeeper.weather.ports

import pl.training.runkeeper.weather.domain.DayForecast

interface ForecastProvider {

    suspend fun getForecast(city: String): List<DayForecast>

}