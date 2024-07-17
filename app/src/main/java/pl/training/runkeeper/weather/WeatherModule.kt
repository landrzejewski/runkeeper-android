package pl.training.runkeeper.weather

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pl.training.runkeeper.RunkeeperDatabase
import pl.training.runkeeper.weather.adapters.persistence.ForecastDao
import pl.training.runkeeper.weather.adapters.persistence.ForecastRoomAdapter
import pl.training.runkeeper.weather.adapters.persistence.ForecastRoomMapper
import pl.training.runkeeper.weather.adapters.provider.FakeForecastProvider
import pl.training.runkeeper.weather.adapters.provider.openweather.OpenWeatherApi
import pl.training.runkeeper.weather.adapters.provider.openweather.OpenWeatherProviderAdapter
import pl.training.runkeeper.weather.adapters.provider.openweather.OpenWeatherProviderMapper
import pl.training.runkeeper.weather.domain.ForecastService
import pl.training.runkeeper.weather.ports.Forecast
import pl.training.runkeeper.weather.ports.ForecastProvider
import pl.training.runkeeper.weather.ports.ForecastRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.BINARY

@Module
@InstallIn(SingletonComponent::class)
class WeatherModule {

    @Test
    @Singleton
    @Provides
    fun fakeForecastProvider(): ForecastProvider = FakeForecastProvider()

    @Singleton
    @Provides
    fun openWeatherApi(httpClient: OkHttpClient): OpenWeatherApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
        .create(OpenWeatherApi::class.java)

    @Singleton
    @Provides
    fun openWeatherProviderMapper(): OpenWeatherProviderMapper = OpenWeatherProviderMapper()

    @Production
    @Singleton
    @Provides
    fun openWeatherProviderAdapter(openWeatherApi: OpenWeatherApi, mapper: OpenWeatherProviderMapper): ForecastProvider
        = OpenWeatherProviderAdapter(openWeatherApi, mapper)

    @Singleton
    @Provides
    fun forecastDao(database: RunkeeperDatabase): ForecastDao = database.forecastDao()

    @Singleton
    @Provides
    fun forecastRoomMapper(): ForecastRoomMapper = ForecastRoomMapper()

    @Singleton
    @Provides
    fun forecastRepository(dao: ForecastDao, mapper: ForecastRoomMapper): ForecastRepository = ForecastRoomAdapter(dao, mapper)

    @Singleton
    @Provides
    fun forecastService(@Production forecastProvider: ForecastProvider, forecastRepository: ForecastRepository): Forecast
        = ForecastService(forecastProvider, forecastRepository)

}

@Qualifier
@Retention(BINARY)
annotation class Test

@Qualifier
@Retention(BINARY)
annotation class Production