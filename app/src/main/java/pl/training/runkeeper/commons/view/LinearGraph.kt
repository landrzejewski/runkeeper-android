package pl.training.runkeeper.commons.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.PropertyValuesHolder.ofFloat
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class LinearGraph(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private val paint: Paint = run {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = Color.LTGRAY
        paint.strokeWidth = 10.0F
        paint
    }
    private val origin = Pair(Point(0F,0F), Point(0F, 0F))
    private val xProperty = "x"
    private val yProperty = "y"
    private var animatedPoints = mutableListOf<Pair<Point, Point>>()
    private var maxX = 0F
    private var maxY = 0F

    fun draw(points: List<Point>) {
        maxX = max(points.map { it.x }.toList())
        maxY = max(points.map { it.y }.toList())
        val pairs = points.zipWithNext()
        animatedPoints = pairs.map { origin }.toMutableList()
        runAnimations(pairs.mapIndexed { index, points -> createAnimator(points, index) })
    }

    private fun runAnimations(animators: List<Animator>) {
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(*animators.toTypedArray())
        animatorSet.startDelay = 2_000
        animatorSet.start()
    }

    private fun createAnimator(points: Pair<Point, Point>, index: Int) = ValueAnimator().apply {
        setValues(ofFloat(xProperty, points.first.x, points.second.x), ofFloat(yProperty, points.first.y, points.second.y))
        duration = 1_000
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            animatedPoints[index] = Pair(points.first, Point(it.getAnimatedValue(xProperty) as Float, it.getAnimatedValue(yProperty) as Float))
            invalidate()
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.translate(0F, height.toFloat())
        canvas.scale(1F, -1F)
        animatedPoints.forEach { drawLine(canvas, it.first, it.second) }
    }

    private fun drawLine(canvas: Canvas, start: Point, end: Point) {
        val path = Path()
        path.moveTo(scaleX(start.x), scaleY(start.y))
        path.lineTo(scaleX(end.x), scaleY(end.y))
        canvas.drawPath(path, paint)
    }

    private fun max(values: List<Float>) = values.maxOrNull() ?: 0f

    private fun scaleX(x: Float) = (x * width) / maxX

    private fun scaleY(y: Float) = (y * height) / maxY

}

data class Point(val x: Float, val y: Float)