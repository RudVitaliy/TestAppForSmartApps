package com.example.tetsappforsmartapps.presentation.gameScreen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.tetsappforsmartapps.R
import com.example.tetsappforsmartapps.domain.entity.BallColor

class GameView(context: Context, attributesSet: AttributeSet?): View(
    context, attributesSet
) {

    private var viewModel: GameViewModel? = null

    fun setViewModel(viewModel: GameViewModel) {
        this.viewModel = viewModel
    }

    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }

    private var screenHeight = 0
    private var screenWidth = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenHeight = h
        screenWidth = w
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val balls = viewModel?.ballsList
        val iterator = balls?.iterator()
        if (iterator != null) {
            while (iterator.hasNext()) {
                val ball = iterator.next()
                paint.color = when (ball.color) {
                    BallColor.BLUE -> ContextCompat.getColor(context, R.color.blue)
                    BallColor.RED -> ContextCompat.getColor(context, R.color.red)
                    BallColor.GREEN -> ContextCompat.getColor(context, R.color.green)
                    BallColor.PURPLE -> ContextCompat.getColor(context, R.color.purple)
                    BallColor.YELLOW -> ContextCompat.getColor(context, R.color.yellow)
                    else -> {throw RuntimeException("There is no colore like this")}
                }
                ball.move()
                if(ball.canRemoved) {
                    iterator.remove()
                }
                canvas?.drawCircle(ball.x, ball.y, ball.radius, paint)
            }
        }
        postInvalidateDelayed(10)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                viewModel?.onTouch(event.x, event.y)
                invalidate()
            }
        }
        return true
    }
}