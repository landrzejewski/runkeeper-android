package pl.training.runkeeper.weather.domain

import java.util.Date

data class DayForecast(
    val data: Date,
    val temperature: Double,
    val pressure: Double,
    val description: String,
    val iconName: String
)