package pl.training.runkeeper

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.training.runkeeper.weather.adapters.persistence.ForecastDao
import pl.training.runkeeper.weather.adapters.persistence.entity.CityEntity
import pl.training.runkeeper.weather.adapters.persistence.entity.DayForecastEntity

@Database(
    entities = [CityEntity::class, DayForecastEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RunkeeperDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

}