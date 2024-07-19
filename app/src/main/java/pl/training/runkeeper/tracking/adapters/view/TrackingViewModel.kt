package pl.training.runkeeper.tracking.adapters.view

import androidx.lifecycle.ViewModel
import pl.training.runkeeper.tracking.ports.Activities
import javax.inject.Inject

class TrackingViewModel @Inject constructor(private val activities: Activities) : ViewModel() {
}