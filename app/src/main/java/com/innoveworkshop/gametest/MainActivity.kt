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

class MainActivity : AppCompatActivity() {
    protected var gameSurface: GameSurface? = null
    protected var upButton: Button? = null
    protected var game: Game? = null
    var ListOfObstacles: MutableList<Circle?> = MutableList(2) {null}

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
        var i = 0
        while (i < ListOfObstacles.size) {
            ListOfObstacles[i] = Circle(
                ((surface!!.width / 2)).toFloat(),
                ((surface.height / 2)+i*400).toFloat(),
                30f,
                Color.BLUE,
                Vector(0f, 0f)
             )
            surface.addGameObject(ListOfObstacles[i]!!)
            i++
        }
        return ListOfObstacles
    }

    inner class Game : GameObject() {
        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            val balls = PlinkoBall(((surface!!.width/2)+40).toFloat(), 20f, ListOfObstacles)
            surface.addGameObject(balls)


            ListOfObstacles = Obstables(surface, ListOfObstacles)
        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()
        }
    }
}