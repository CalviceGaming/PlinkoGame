package com.innoveworkshop.gametest

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Physics

class MainActivity : AppCompatActivity() {
    protected var gameSurface: GameSurface? = null
    protected var upButton: Button? = null
    protected var downButton: Button? = null
    protected var leftButton: Button? = null
    protected var rightButton: Button? = null

    protected var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameSurface = findViewById<View>(R.id.gameSurface) as GameSurface
        Obstables(gameSurface)
        game = Game()
        gameSurface!!.setRootGameObject(game)

        setupControls()
    }

    private fun setupControls() {
        upButton = findViewById<View>(R.id.SpawnBall) as Button
        upButton!!.setOnClickListener {PlinkoBall((gameSurface!!.width/2).toFloat())}


    }


    public fun Obstables(surface: GameSurface?){
        var circle: Circle? = null
        circle = Circle(
            (surface!!.width / 2).toFloat(),
            (surface.height / 2).toFloat(),
            30f,
            Color.BLACK
        )
    }


    inner class Game : GameObject() {
        var circle: Circle? = null
        var time = 0f
        var deltaTime = 0.016f
        var InitialY = 0f

        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            circle = Circle(
                (surface!!.width / 2).toFloat(),
                (surface.height / 2).toFloat(),
                30f,
                Color.BLACK
            )
            surface.addGameObject(circle!!)

            if (position != null) {
                InitialY = position.y
            }
        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()

            if (circle?.isFloored == false) {
                circle!!.position.y = Physics().ChnageY(
                    InitialY,
                    0f,
                    time
                )
                time += deltaTime

            } else {
                time = 0f
            }
        }
    }
}