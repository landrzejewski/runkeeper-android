package pl.training.runkeeper.weather

import pl.training.runkeeper.weather.adapters.provider.FakeForecastProvider
import pl.training.runkeeper.weather.domain.ForecastService
import pl.training.runkeeper.weather.ports.Forecast

private val fakeForecastProvider by lazy { FakeForecastProvider() }

private val forecastService by lazy { ForecastService(fakeForecastProvider) }

object Container {

    fun forecastService(): Forecast = forecastService

}
