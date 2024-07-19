package pl.training.runkeeper.tracking.domain

data class ActivityPoint(
    val id: String,
    val timestamp: Long,
    val distance: Float,
    val speed: Float,
    val pace: Double,
    val duration: Long,
    val position: Position
)