package com.innoveworkshop.gametest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.assets.PlinkoBall
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Physics
import com.innoveworkshop.gametest.engine.Vector
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    protected var gameSurface: GameSurface? = null
    protected var upButton: Button? = null
    protected var game: Game? = null
    var ListOfObstacles: MutableList<Circle?> = MutableList(30) {null}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameSurface = findViewById<View>(R.id.gameSurface) as GameSurface
        game = Game()
        gameSurface!!.setRootGameObject(game)


        setupControls()
    }

    public fun setupControls() {
        upButton = findViewById<View>(R.id.SpawnBall) as Button
        upButton!!.setOnClickListener {
            val balls = PlinkoBall((gameSurface!!.width/2).toFloat(),20f, ListOfObstacles)
            gameSurface!!.addGameObject(balls)
        }
    }


    fun Obstables(surface: GameSurface?, ListOfObstacles: MutableList<Circle?>):MutableList<Circle?>{
        var line = 1
        val maxLines = 6
        var balls = 0
        var ballsInLine = 0
        val initialY = surface!!.height.toFloat() * (1/4f)
        val surfaceWidth = surface.width.toFloat()
        val d = 250f
        //val ballX = initialX + (ballsInLine * d)
        while (line < maxLines) {
            val initialX = (surfaceWidth - ((line - 1 ) * d))/2
            while (ballsInLine < line) {
                val ballY = initialY + (sqrt((d * d) - ((d / 2) * (d / 2))) * line)
                val ballX = initialX + (ballsInLine * d)

                ListOfObstacles[balls] = Circle(
                    ballX,
                    ballY,
                    30f,
                    Color.BLUE,
                    Vector(0f, 0f)
                )

                surface.addGameObject(ListOfObstacles[balls]!!)
                balls++
                ballsInLine++
            }
            ballsInLine = 0
            line++
        }
        return ListOfObstacles
    }

    inner class Game : GameObject() {
        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            //val balls = PlinkoBall(((surface!!.width/2)+40).toFloat(), 20f, ListOfObstacles)
            //surface.addGameObject(balls)

            ListOfObstacles = Obstables(surface, ListOfObstacles)
        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()
        }
    }
}