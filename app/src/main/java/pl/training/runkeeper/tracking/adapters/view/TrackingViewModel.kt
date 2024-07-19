package pl.training.runkeeper.tracking.adapters.view

import androidx.lifecycle.ViewModel
import pl.training.runkeeper.tracking.domain.Activity
import pl.training.runkeeper.tracking.domain.Position
import pl.training.runkeeper.tracking.ports.Activities
import javax.inject.Inject

class TrackingViewModel @Inject constructor(private val activities: Activities) : ViewModel() {

    private val activity = Activity()

    fun startActivity(position: Position) {
        activity.start(position)
    }

    fun onLocationUpdate(distanceChange: Float, speed: Float, position: Position) {
        activity.addActivityPoint(distanceChange, speed, position)
    }

}