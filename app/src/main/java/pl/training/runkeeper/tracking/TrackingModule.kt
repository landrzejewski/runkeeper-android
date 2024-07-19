package pl.training.runkeeper.tracking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.training.runkeeper.tracking.domain.ActivityService
import pl.training.runkeeper.tracking.ports.Activities
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class TrackingModule {

    @Singleton
    @Provides
    fun activities(): Activities = ActivityService()

}