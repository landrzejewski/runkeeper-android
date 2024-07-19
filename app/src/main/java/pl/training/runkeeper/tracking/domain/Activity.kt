package pl.training.runkeeper.tracking.domain

data class Activity(
    val id: String,
    val type: ActivityType,
    val activityPoints: MutableList<ActivityPoint> = mutableListOf()
)