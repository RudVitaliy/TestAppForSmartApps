package com.example.tetsappforsmartapps.presentation.gameScreen

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tetsappforsmartapps.data.repositoryImpl.RepositoryImpl
import com.example.tetsappforsmartapps.domain.entity.Ball
import com.example.tetsappforsmartapps.domain.entity.BallColor
import com.example.tetsappforsmartapps.domain.entity.GameSettings
import com.example.tetsappforsmartapps.domain.entity.Level
import kotlin.random.Random

class GameViewModel(
    private val level: Level,
    private val application: Application
): ViewModel() {

    private val repository = RepositoryImpl(application)

    private lateinit var gameSettings: GameSettings

    private var timer: CountDownTimer? = null

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _running = MutableLiveData<Boolean>()
    val running: LiveData<Boolean>
        get() = _running

    private var maxXSaved = 0
    private var maxYSaved = 0
    private var ballsScore = 0

    val ballsList: MutableList<Ball> = ArrayList()

    fun onTouch(x: Float, y: Float) {
        updateScore(x, y)
        addBall(maxXSaved, maxYSaved)
    }

    private fun removeBall(x: Float, y: Float) {
        ballsList.removeIf { it.contains(x, y) && it.color == BallColor.BLUE }
    }

    private fun updateScore(x: Float, y: Float) {
        val ballListClone = ballsList.toMutableList()
        ballListClone.forEach {
            if (it.contains(x,y) && it.color == BallColor.BLUE) {
                removeBall(x,y)
                ballsScore++
            }
            if (it.contains(x,y) && it.color != BallColor.BLUE) {
                ballsScore--
            }
        }
        _score.value = ballsScore
    }

    private fun startTimer() {
        _running.value = true
        val timeInSec = gameSettings.timeInSec
        timer = object : CountDownTimer( timeInSec * MILLIS_IN_SECONDS, MILLIS_IN_SECONDS){
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }
            override fun onFinish() {
                _running.value = false
            }
        }
        timer?.start()
    }

    fun startGame(maxX: Int, maxY: Int) {
        getGameSettings()
        startTimer()
        generateBalls(maxX, maxY)
    }

    private fun getGameSettings() {
        this.gameSettings = repository.getGameSettings(level)
    }

    fun update() {
        if (ballsList.size < 10) {
            generateBalls(maxXSaved, maxYSaved)
        }
    }

    private fun generateBalls(maxX: Int, maxY: Int) {
        val maxBalls = gameSettings.countOfBalls
        maxXSaved = maxX
        maxYSaved = maxY
        while (ballsList.size < maxBalls) {
            addBall(maxX, maxY)
        }
    }

    private fun addBall(maxX: Int, maxY: Int) {
        val x = Random.nextInt(100, maxX - 100)
        val y = maxY.toFloat()
        val radius = 100f
        val speed = Random.nextInt(4, 10)
        val color = getColor()
        val ball = Ball(x.toFloat(), y, radius, color, speed.toFloat())
        ballsList.add(ball)
    }

    private fun getColor(): BallColor {
        return when(Random.nextInt(gameSettings.countOfColors + 2)) {
            0 -> BallColor.BLUE
            1 -> BallColor.RED
            2 -> BallColor.GREEN
            3 -> BallColor.YELLOW
            4 -> BallColor.PURPLE
            5 -> BallColor.BLUE
            6 -> BallColor.BLUE
            else -> BallColor.BLUE
        }
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }

}