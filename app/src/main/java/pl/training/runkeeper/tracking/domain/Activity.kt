package pl.training.runkeeper.tracking.domain

import pl.training.runkeeper.commons.generateId
import pl.training.runkeeper.tracking.domain.ActivityType.RUNNING

data class Activity(
    private val id: String = generateId(),
    private val type: ActivityType = RUNNING) {

    private val activityPoints = mutableListOf<ActivityPoint>()

    fun start(position: Position) {
       addActivityPoint(0.0F, 0.0F, position)
    }

    fun addActivityPoint(distanceChange: Float, speed: Float, position: Position): ActivityPoint{
        val activityPoint = createActivityPoint(distanceChange, speed, position)
        activityPoints.add(activityPoint)
        return activityPoint
    }

    private fun createActivityPoint(distanceChange: Float, speed: Float, position: Position): ActivityPoint {
        val distance = if (activityPoints.size > 0) activityPoints.last().distance + distanceChange else 0.0F
        val duration = getDuration()
        val pace = if (distance > 0) duration.toDouble() / (1_000 * 60) / (distance / 1_000) else 0.0
        return ActivityPoint(distance = distance, speed = speed, pace = pace, duration = duration, position = position)
    }

    private fun getDuration() = if (activityPoints.isEmpty()) 0 else System.currentTimeMillis() - activityPoints.first().timestamp

    fun getLastActivityPoint(): ActivityPoint? = activityPoints.lastOrNull()

}