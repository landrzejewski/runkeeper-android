package pl.training.runkeeper.weather.domain

import pl.training.runkeeper.weather.ports.Forecast
import pl.training.runkeeper.weather.ports.ForecastProvider
import pl.training.runkeeper.weather.ports.ForecastRepository
import pl.training.runkeeper.weather.ports.RefreshForecastFailedException

class ForecastService(
    private val forecastProvider: ForecastProvider,
    private val forecastRepository: ForecastRepository
): Forecast {

    override suspend fun getForecast(city: String) = try {
        val forecast = forecastProvider.getForecast(city)
        forecastRepository.deleteAll()
        forecastRepository.save(city, forecast)
        forecast
    } catch (exception: Exception) {
        throw RefreshForecastFailedException()
    }

    override suspend fun getCachedForecast(city: String) = forecastRepository.findByCity(city)

}