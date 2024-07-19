package pl.training.runkeeper.tracking.domain

import pl.training.runkeeper.commons.generateId

data class ActivityPoint(
    val id: String = generateId(),
    val timestamp: Long = System.currentTimeMillis(),
    val distance: Float,
    val speed: Float,
    val pace: Double,
    val duration: Long,
    val position: Position
)