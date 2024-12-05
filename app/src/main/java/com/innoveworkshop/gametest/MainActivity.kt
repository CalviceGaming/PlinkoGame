package com.innoveworkshop.gametest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.assets.Multiplier
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
    var ListOfMultipliers: MutableList<Multiplier?> = MutableList(10) {null}
    var ListOfBallsinJaw: MutableList<PlinkoBall?> = MutableList(50) {null}
    var iBall = 0
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
                if (iBall == 50){
                    iBall = 0
                }
                val randomValue =
                    Random.nextInt((gameSurface!!.width / 2) - 100, (gameSurface!!.width / 2 + 100))

                ListOfBallsinJaw[iBall] = PlinkoBall(randomValue.toFloat(), 20f, ListOfObstacles, ListOfMultipliers)
                gameSurface!!.addGameObject(ListOfBallsinJaw[iBall]!!)
                iBall++
                peso -= 5f
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

    fun Multipliers(surface: GameSurface?, ListOfMultipliers: MutableList<Multiplier?>): MutableList<Multiplier?>{
        var i = 0
        var height = 10f
        var width = gameSurface!!.width.toFloat()/10
        while (i < ListOfMultipliers.size){
            var color = Color.YELLOW
            var multiplier = 10f
            if (i < 4 || i > 5){
                multiplier = 4f
                color = Color.MAGENTA
            }
            if (i < 3 || i > 6){
                multiplier = 2f
                color = Color.GREEN
            }
            if (i < 2 || i > 7){
                multiplier = 0.2f
                color = Color.RED
            }
            Log.d("Mult: ", multiplier.toString())
            ListOfMultipliers[i] = Multiplier(Vector((width/2) + width*i,gameSurface!!.height - height/2),width, height, color, multiplier)
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
            var ball = PlinkoBall(surface!!.width.toFloat()/2, 20f, ListOfObstacles, ListOfMultipliers)
            surface.addGameObject(ball)
        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()
            pesoText?.setText("${peso}$")
            var i = 0
            while (i < ListOfBallsinJaw.size){
                if(ListOfBallsinJaw[i] != null){
                    if (ListOfBallsinJaw[i]!!.isFloored && ListOfBallsinJaw[i]!!.mulaCounted == 1){
                        peso += ListOfBallsinJaw[i]!!.mula
                        ListOfBallsinJaw[i]!!.mulaCounted = 0
                    }
                }
                i++
            }
        }
    }
}