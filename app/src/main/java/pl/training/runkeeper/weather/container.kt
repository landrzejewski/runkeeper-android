package pl.training.runkeeper.weather

import pl.training.runkeeper.weather.adapters.provider.FakeForecastProvider
import pl.training.runkeeper.weather.adapters.provider.openweather.OpenWeatherApi
import pl.training.runkeeper.weather.adapters.provider.openweather.OpenWeatherProviderAdapter
import pl.training.runkeeper.weather.adapters.provider.openweather.OpenWeatherProviderMapper
import pl.training.runkeeper.weather.domain.ForecastService
import pl.training.runkeeper.weather.ports.Forecast
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val fakeForecastProvider by lazy { FakeForecastProvider() }

private val openWeatherApi = Retrofit.Builder()
    .baseUrl("https://api.openweathermap.org/data/2.5/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(OpenWeatherApi::class.java)

private val openWeatherProviderMapper by lazy { OpenWeatherProviderMapper() }

private val openWeatherProviderAdapter by lazy { OpenWeatherProviderAdapter(openWeatherApi, openWeatherProviderMapper) }

private val forecastService by lazy { ForecastService(openWeatherProviderAdapter) }

object Container {

    fun forecastService(): Forecast = forecastService

}
