package pl.training.runkeeper.weather.adapters.persistence

import pl.training.runkeeper.weather.domain.DayForecast
import pl.training.runkeeper.weather.ports.ForecastRepository

class ForecastRoomAdapter(
    private val dao: ForecastDao,
    private val mapper: ForecastRoomMapper
) : ForecastRepository {

    override suspend fun save(city: String, forecast: List<DayForecast>) {
        val cityEntity = mapper.toEntity(city)
        dao.save(cityEntity)
        val forecastEntities = forecast.map { mapper.toEntity(city, it) }
        dao.save(forecastEntities)
    }

    override suspend fun findByCity(city: String) = dao.findByCity(city)
        ?.forecast?.map(mapper::toModel) ?: emptyList()

    override suspend fun deleteAll() = dao.deleteAll()

}