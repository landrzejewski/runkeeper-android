package pl.training.runkeeper.weather.adapters.provider

import pl.training.runkeeper.weather.domain.DayForecast
import pl.training.runkeeper.weather.ports.ForecastProvider
import java.util.Date

class FakeForecastProvider: ForecastProvider {

    override suspend fun getForecast(city: String) = listOf(
        DayForecast(Date(), 24.0, 1023.0, "Clear sky", "ic_sun"),
        DayForecast(Date(), 26.0, 1024.0, "Clouds", "ic_cloud_sun"),
        DayForecast(Date(), 22.0, 1022.0, "Rain", "ic_cloud_rain")
    )

}