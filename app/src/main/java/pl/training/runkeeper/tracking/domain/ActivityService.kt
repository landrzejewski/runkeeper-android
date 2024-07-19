package pl.training.runkeeper.tracking.domain

import pl.training.runkeeper.tracking.ports.Activities

class ActivityService : Activities {

    override fun createActivity(type: ActivityType) = Activity()

}