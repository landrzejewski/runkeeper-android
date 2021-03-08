package pl.training.runkeeper.configuration

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.training.runkeeper.forecast.adapters.persistence.CityDb
import pl.training.runkeeper.forecast.adapters.persistence.DayForecastDb
import pl.training.runkeeper.forecast.adapters.persistence.ForecastDao

@Database(entities = [CityDb::class, DayForecastDb::class], version = 5, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

}