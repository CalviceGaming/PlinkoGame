package com.innoveworkshop.gametest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.assets.PlinkoBall
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Physics
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector
import org.w3c.dom.Text
import kotlin.math.sqrt
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    protected var gameSurface: GameSurface? = null
    protected var upButton: Button? = null
    protected var game: Game? = null
    val maxLines = 7
    val numberOfObs = (maxLines*(maxLines+1))/2
    var ListOfObstacles: MutableList<Circle?> = MutableList(numberOfObs) {null}
    var ListOfMultipliers: MutableList<Rectangle?> = MutableList(10) {null}
    var peso = 25f
    var pesoText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pesoText = findViewById<TextView>(R.id.PesoText)
        gameSurface = findViewById<View>(R.id.gameSurface) as GameSurface
        game = Game()
        gameSurface!!.setRootGameObject(game)

    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                val randomValue =
                    Random.nextInt((gameSurface!!.width / 2) - 100, (gameSurface!!.width / 2 + 100))

                val balls = PlinkoBall(randomValue.toFloat(), 20f, ListOfObstacles)
                gameSurface!!.addGameObject(balls)
            }
        }
        return true
    }


    fun Obstables(surface: GameSurface?, ListOfObstacles: MutableList<Circle?>):MutableList<Circle?>{
        var line = 1
        var balls = 0
        var ballsInLine = 0
        val initialY = surface!!.height.toFloat() * (1/4f)
        val surfaceWidth = surface.width.toFloat()
        val d = 250f
        //val ballX = initialX + (ballsInLine * d)
        while (line <= maxLines) {
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

    fun Multipliers(surface: GameSurface?, ListOfMultipliers: MutableList<Rectangle?>): MutableList<Rectangle?>{
        var i = 0
        var height = 10f
        var width = 80f
        while (i <= ListOfMultipliers.size){
            ListOfMultipliers[i] = Rectangle(Vector(width/2f,gameSurface!!.height - height/2),width, height, Color.RED)
            gameSurface!!.addGameObject(ListOfMultipliers[i]!!)
            i++
        }
        return ListOfMultipliers
    }

    inner class Game : GameObject() {
        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            ListOfObstacles = Obstables(surface, ListOfObstacles)
            ListOfMultipliers = Multipliers(surface, ListOfMultipliers)
        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()
            pesoText?.setText("${peso}$")
        }
    }
}