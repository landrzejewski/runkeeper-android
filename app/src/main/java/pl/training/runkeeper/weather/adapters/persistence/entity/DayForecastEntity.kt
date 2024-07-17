package pl.training.runkeeper.weather.adapters.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class DayForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val temperature: Double,
    val pressure: Double,
    val date: Date,
    val description: String,
    @ColumnInfo(name = "icon_name", defaultValue = "ic_empty") val iconName: String,
    val cityName: String
)