package pl.training.runkeeper

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import pl.training.runkeeper.commons.AppIdInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RunkeeperModule {

    @Singleton
    @Provides
    fun httpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = Level.BASIC
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(AppIdInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()
    }

}