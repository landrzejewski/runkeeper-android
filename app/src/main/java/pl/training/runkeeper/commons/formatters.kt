package pl.training.runkeeper.commons

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Date, format: String = "dd/MM"): String = SimpleDateFormat(format, Locale.getDefault()).format(date)

fun formatDegrees(value: Int): String = "$value °"

fun formatSpeed(speed: Float) = String.format("%.2f km/h", speed * 18 / 5)

fun formatPace(pace: Double) = String.format("%.2f min/km", pace)

fun formatTime(time: Long): String {
    var secs = (time / 1_000).toInt()
    val hours = secs / 3_6000
    val mins = secs % 3_6000 / 60
    secs %= 60
    return String.format("%02d:%02d:%02d", hours, mins, secs)
}