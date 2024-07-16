package pl.training.runkeeper.weather.adapters.provider.openweather

import pl.training.runkeeper.weather.adapters.provider.openweather.dto.DayForecastDto
import pl.training.runkeeper.weather.domain.DayForecast
import java.util.Date

class OpenWeatherProviderMapper {

    private val icons = mapOf(
        "01d" to "ic_sun",
        "02d" to "ic_cloud_sun",
        "03d" to "ic_cloud",
        "04d" to "ic_cloud",
        "09d" to "ic_cloud_rain",
        "10d" to "ic_cloud_sun_rain",
        "11d" to "ic_bolt",
        "13d" to "ic_snowflake",
        "50d" to "ic_wind"
    )

    fun toModel(dayForecastDto: DayForecastDto) = with(dayForecastDto) {
        val conditions = weather.first()
        val iconName = icons[conditions.icon] ?: "ic_empty"
        val forecastDate = Date(date * 1_000)
        DayForecast(forecastDate, temperature.day, pressure, conditions.description, iconName)
    }

}