package pl.training.runkeeper.weather.ports

import pl.training.runkeeper.weather.domain.DayForecast

interface Forecast {

    suspend fun getForecast(city: String): List<DayForecast>

}