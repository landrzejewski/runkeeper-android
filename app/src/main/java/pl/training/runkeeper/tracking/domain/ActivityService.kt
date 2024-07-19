package pl.training.runkeeper.tracking.domain

import pl.training.runkeeper.tracking.ports.Activities
import java.util.UUID

class ActivityService : Activities {

    override fun startActivity(type: ActivityType) = Activity(idGenerator(), type)

    private fun idGenerator() = UUID.randomUUID().toString()

}