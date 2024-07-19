package pl.training.runkeeper.tracking.ports

import pl.training.runkeeper.tracking.domain.Activity
import pl.training.runkeeper.tracking.domain.ActivityType

interface Activities {

    fun createActivity(type: ActivityType): Activity

}