package com.example.tetsappforsmartapps.domain.entity

data class Ball(
    var x: Float,
    var y: Float,
    var radius: Float,
    var color: BallColor,
    var speed: Float,
    var isTouched: Boolean = false,
    var canRemoved: Boolean = false
    ){
    fun move() {
        y -= speed
        if (y < 100) {
            canRemoved = true
        }
    }

    fun contains(x: Float, y: Float): Boolean {
        val dX = x - this.x
        val dY = y - this.y
        return dX * dX + dY * dY <= radius * radius
    }
}
