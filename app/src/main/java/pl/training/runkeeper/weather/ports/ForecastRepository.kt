package pl.training.runkeeper.weather.ports

import pl.training.runkeeper.weather.domain.DayForecast

interface ForecastRepository {

    suspend fun save(city: String, forecast: List<DayForecast>)

    suspend fun findByCity(city: String): List<DayForecast>

    suspend fun deleteAll()

}