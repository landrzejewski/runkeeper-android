package pl.training.runkeeper.tracking.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.training.runkeeper.commons.formatPace
import pl.training.runkeeper.commons.formatSpeed
import pl.training.runkeeper.commons.formatTime
import pl.training.runkeeper.tracking.domain.ActivityType.RUNNING
import pl.training.runkeeper.tracking.domain.Position
import pl.training.runkeeper.tracking.ports.Activities
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(activities: Activities) : ViewModel() {

    private val activity = activities.createActivity(RUNNING)
    private val trackingStats = MutableLiveData<TracingStatsViewModel>()

    val stats: LiveData<TracingStatsViewModel> = trackingStats

    fun startActivity(position: Position) {
        activity.start(position)
    }

    fun onLocationUpdate(distanceChange: Float, speed: Float, position: Position) {
        val activityPoint = activity.addActivityPoint(distanceChange, speed, position)
        val stats = TracingStatsViewModel(
            formatTime(activityPoint.duration),
            formatSpeed(activityPoint.speed),
            formatPace(activityPoint.pace)
        )
        trackingStats.postValue(stats)
    }

    fun getLastLocation(): Position? = activity.getLastActivityPoint()?.position

}