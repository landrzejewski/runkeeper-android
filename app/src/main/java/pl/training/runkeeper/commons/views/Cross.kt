package pl.training.runkeeper.commons.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class Cross(context: Context, attributes: AttributeSet) : View(context, attributes) {

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        val fillPaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.GRAY
            strokeWidth = 3F
            isAntiAlias = true
        }
        val borderPaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.DKGRAY
            textSize = 50F
            isAntiAlias = true
        }
        val linePaint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.RED
            strokeWidth = 3F
            isAntiAlias = true
        }
        val rect = RectF(100F,100F, 400F, 200F)
        canvas?.let {
            it.drawRect(rect, borderPaint)
            rect.inset(5F, 5F)
            it.drawRoundRect(rect, 10F, 10F, fillPaint)
            it.drawText("Button", 160F, 170F, borderPaint)


            val path = Path()
            path.moveTo(200F, 500F)
            path.lineTo(200F, 700F)
            path.lineTo(100F, 700F)
            path.lineTo(100F, 500F)
            path.lineTo(200F, 500F)
            path.close()
            it.drawPath(path, borderPaint)

            val curvePath = Path().apply {
                moveTo(34F, 254F)
                cubicTo(68F, 150F, 280F, 350F, 336F, 252F)
            }
            path.close()
            it.drawPath(curvePath, linePaint)
        }

    }

}

