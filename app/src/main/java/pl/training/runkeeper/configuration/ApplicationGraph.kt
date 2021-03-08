package pl.training.runkeeper.configuration

import dagger.Component
import pl.training.runkeeper.forecast.ForecastModule
import pl.training.runkeeper.forecast.viewmodels.ForecastViewModel
import pl.training.runkeeper.forecast.views.ForecastListFragment
import pl.training.runkeeper.tracking.TrackingModule
import pl.training.runkeeper.tracking.views.TrackingFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ForecastModule::class, TrackingModule::class])
interface ApplicationGraph {

    fun inject(forecastViewModel: ForecastViewModel)

    fun inject(forecastListFragment: ForecastListFragment)

    fun inject(trackingFragment: TrackingFragment)

}