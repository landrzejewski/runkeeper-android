package pl.training.runkeeper

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import pl.training.runkeeper.commons.AppIdInterceptor
import pl.training.runkeeper.commons.store.SharedPreferencesStore
import pl.training.runkeeper.commons.store.Store
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

    @Singleton
    @Provides
    fun store(@ApplicationContext context: Context): Store = SharedPreferencesStore(context)

    @Singleton
    @Provides
    fun database(@ApplicationContext context: Context): RunkeeperDatabase = Room
        .databaseBuilder(context, RunkeeperDatabase::class.java, "runkeeper")
        .fallbackToDestructiveMigration()
        .build()

}