package com.example.canvastry

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlin.math.min


class CustomProgressBar: View {
    val TAG = this::class.java.canonicalName

    private var maxProgress = 100
    private var progress = 0
    private var countdown = 0
    private var progressPaint: Paint? = null
    private var backgroundPaint: Paint? = null
    private var textPaint: Paint? = null
    private var textSize = 0f
    private var progressColor = 0

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        init()

        val width = width
        val height = height
        val cornerRadius = 30f // Adjust corner radius as needed

        // Define a rectangle for the progress bar bounds
        val rectF = RectF(20f, 20f, (width - 20).toFloat(), (height - 20).toFloat())

        // Draw background rectangle with rounded corners
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, backgroundPaint!!)

        // Calculate the total length of the perimeter and the progress length
        val totalLength = 2 * (width - 40) + 2 * (height - 40) - 4 * cornerRadius
        val progressLength = totalLength * progress / maxProgress

        // Create a path for the progress
        val path = Path()
        path.moveTo(20f + cornerRadius, 20f)
        var remainingLength = progressLength

        // Handle 100% progress separately
        if (progress == maxProgress) {
            // Draw the full perimeter path
            path.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)
        } else {
            // Top side
            if (remainingLength >= width - 40 - 2 * cornerRadius) {
                path.lineTo((width - 20 - cornerRadius).toFloat(), 20f)
                remainingLength -= (width - 40 - 2 * cornerRadius)
            } else {
                path.lineTo(20f + cornerRadius + remainingLength, 20f)
                remainingLength = 0f
            }

            // Top-right corner
            if (remainingLength > 0) {
                path.arcTo(RectF((width - 40 - cornerRadius).toFloat(), 20f, (width - 20).toFloat(), (40 + cornerRadius).toFloat()), -90f, 90f)
                remainingLength -= cornerRadius
            }

            // Right side
            if (remainingLength >= height - 40 - 2 * cornerRadius) {
                path.lineTo((width - 20).toFloat(), (height - 20 - cornerRadius).toFloat())
                remainingLength -= (height - 40 - 2 * cornerRadius)
            } else if (remainingLength > 0) {
                path.lineTo((width - 20).toFloat(), 20f + cornerRadius + remainingLength)
                remainingLength = 0f
            }

            // Bottom-right corner
            if (remainingLength > 0) {
                path.arcTo(RectF((width - 40 - cornerRadius).toFloat(), (height - 40 - cornerRadius).toFloat(), (width - 20).toFloat(), (height - 20).toFloat()), 0f, 90f)
                remainingLength -= cornerRadius
            }

            // Bottom side
            if (remainingLength >= width - 40 - 2 * cornerRadius) {
                path.lineTo((20 + cornerRadius).toFloat(), (height - 20).toFloat())
                remainingLength -= (width - 40 - 2 * cornerRadius)
            } else if (remainingLength > 0) {
                path.lineTo((width - 20 - cornerRadius - remainingLength).toFloat(), (height - 20).toFloat())
                remainingLength = 0f
            }

            // Bottom-left corner
            if (remainingLength > 0) {
                path.arcTo(RectF(20f, (height - 40 - cornerRadius).toFloat(), (40 + cornerRadius).toFloat(), (height - 20).toFloat()), 90f, 90f)
                remainingLength -= cornerRadius
            }

            // Left side
            if (remainingLength >= height - 40 - 2 * cornerRadius) {
                path.lineTo(20f, (20 + cornerRadius).toFloat())
                remainingLength -= (height - 40 - 2 * cornerRadius)
            } else if (remainingLength > 0) {
                path.lineTo(20f, (height - 20 - cornerRadius - remainingLength))
                remainingLength = 0f
            }
        }

        // Draw the progress path
        canvas.drawPath(path, progressPaint!!)

        // Draw countdown text in the center
        val text = countdown.toString()
        canvas.drawText(
            text,
            (width / 2).toFloat(),
            (height / 2 - (textPaint!!.descent() + textPaint!!.ascent()) / 2),
            textPaint!!
        )

//        val width = width
//        val height = height
//        val cornerRadius = 30f // Adjust corner radius as needed
//
//        // Define a rectangle for the progress bar bounds
//        val rectF = RectF(20f, 20f, (width - 20).toFloat(), (height - 20).toFloat())
//
//        // Draw background rectangle with rounded corners
//        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, backgroundPaint!!)
//
//        // Calculate the total length of the perimeter and the progress length
//        val totalLength = 2 * (width - 40) + 2 * (height - 40) - 4 * cornerRadius
//        val progressLength = totalLength * progress / maxProgress
//
//        // Create a path for the progress
//        val path = Path()
//        path.moveTo(20f + cornerRadius, 20f)
//        var remainingLength = progressLength
//
//        // Function to draw lines and arcs
//        fun addLineAndArc(lineLength: Float, x1: Float, y1: Float, x2: Float, y2: Float, arcRect: RectF?, startAngle: Float, sweepAngle: Float) {
//            if (remainingLength >= lineLength) {
//                path.lineTo(x2, y2)
//                remainingLength -= lineLength
//            } else if (remainingLength > 0) {
//                val ratio = remainingLength / lineLength
//                path.lineTo(x1 + (x2 - x1) * ratio, y1 + (y2 - y1) * ratio)
//                remainingLength = 0f
//            }
//            if (arcRect != null && remainingLength > 0) {
//                path.arcTo(arcRect, startAngle, sweepAngle)
//                remainingLength -= cornerRadius
//            }
//        }
//
//        // Top side
//        addLineAndArc(width - 40 - 2 * cornerRadius, 20f + cornerRadius, 20f, width - 20 - cornerRadius, 20f, null, 0f, 0f)
//
//        // Top-right corner
//        addLineAndArc(cornerRadius, width - 20 - cornerRadius, 20f, width - 20f, 20f + cornerRadius,
//            RectF((width - 40 - cornerRadius).toFloat(), 20f, (width - 20).toFloat(), (40 + cornerRadius).toFloat()),
//            -90f, 90f)
//
//        // Right side
//        addLineAndArc(height - 40 - 2 * cornerRadius, width - 20f, 20f + cornerRadius, width - 20f, height - 20 - cornerRadius, null, 0f, 0f)
//
//        // Bottom-right corner
//        addLineAndArc(cornerRadius, width - 20f, height - 20 - cornerRadius, width - 20f, height - 20f,
//            RectF((width - 40 - cornerRadius).toFloat(), (height - 40 - cornerRadius).toFloat(), (width - 20).toFloat(), (height - 20).toFloat()),
//            0f, 90f)
//
//        // Bottom side
//        addLineAndArc(width - 40 - 2 * cornerRadius, width - 20 - cornerRadius, height - 20f, 20f + cornerRadius, height - 20f, null, 0f, 0f)
//
//        // Bottom-left corner
//        addLineAndArc(cornerRadius, 20f + cornerRadius, height - 20f, 20f, height - 20f,
//            RectF(20f, (height - 40 - cornerRadius).toFloat(), (40 + cornerRadius).toFloat(), (height - 20).toFloat()),
//            90f, 90f)
//
//        // Left side
//        addLineAndArc(height - 40 - 2 * cornerRadius, 20f, height - 20 - cornerRadius, 20f, 20f + cornerRadius, null, 0f, 0f)
//
//        // Draw the progress path
//        canvas.drawPath(path, progressPaint!!)
//
//        // Draw countdown text in the center
//        val text = countdown.toString()
//        canvas.drawText(
//            text,
//            (width / 2).toFloat(),
//            (height / 2 - (textPaint!!.descent() + textPaint!!.ascent()) / 2),
//            textPaint!!
//        )
    }

    fun init() {

        progressPaint = Paint()
        backgroundPaint = Paint()
        textPaint = Paint()

        progressPaint?.let {
            it.color = progressColor
            it.setStyle(Paint.Style.STROKE)
            it.setStrokeWidth(20f)
            it.setAntiAlias(true)
        }

        backgroundPaint?.let {
            it.color = Color.GRAY
            it.setStyle(Paint.Style.STROKE)
            it.setStrokeWidth(20f)
            it.setAntiAlias(true)
        }

        textPaint?.let {
            it.color = Color.BLACK
            it.setTextSize(textSize)
            it.setTypeface(ResourcesCompat.getFont(context, R.font.kamerik_205_bold))
            it.setAntiAlias(true)
            it.setTextAlign(Paint.Align.CENTER)
        }
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    fun setCountdown(countdown: Int) {
        this.countdown = countdown
        invalidate()
    }

    fun setMaxProgress(maxProgress: Int) {
        this.maxProgress = maxProgress
        invalidate()
    }

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
    }

    fun setProgressColor(color: Int) {
        this.progressColor = color
    }
}