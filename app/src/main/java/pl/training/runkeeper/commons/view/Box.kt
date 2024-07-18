package pl.training.runkeeper.commons.view

import android.animation.AnimatorSet
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class Box(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private val size = 150
    private fun getPaint(): Paint {
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.color = Color.LTGRAY
        paint.strokeWidth = 10.0F
        paint.alpha = alpha
        return paint
    }
    private val radiusProperty = "radius"
    private val propertyRadius = PropertyValuesHolder.ofFloat(radiusProperty, 0F, 60F)
    private var radius = 0F

    private val rotationProperty = "rotation"
    private val propertyRotation = PropertyValuesHolder.ofFloat(rotationProperty, 0F, 360F)
    private var angle = 0F

    private var alpha = 255

    init {
        // val valueAnimator = ValueAnimator.ofFloat(0.0F, 100F)
        val valueAnimator = ValueAnimator()
        valueAnimator.setValues(propertyRadius, propertyRotation)
        valueAnimator.duration = 1_000
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.addUpdateListener {
            radius = it.getAnimatedValue(radiusProperty) as Float
            angle = it.getAnimatedValue(rotationProperty) as Float
            invalidate()
        }
        // valueAnimator.start()
        val fadeAnimator = ValueAnimator.ofInt(255, 0).apply {
            duration = 800
            addUpdateListener {
                alpha = it.animatedValue as Int
                invalidate()
            }
            startDelay = 500
        }
        val animations = AnimatorSet()
        animations.playTogether(valueAnimator, fadeAnimator)
        animations.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerY = height / 2.0F
        val centerX = width / 2.0F
        val leftTopX = centerX - size
        val leftTopY = centerY - size
        val rightBottomX = centerX + size
        val rightBottomY = centerY + size
        canvas.rotate(angle, centerX, centerY)
        canvas.drawRoundRect(leftTopX, leftTopY, rightBottomX, rightBottomY, radius, radius, getPaint())
    }

}