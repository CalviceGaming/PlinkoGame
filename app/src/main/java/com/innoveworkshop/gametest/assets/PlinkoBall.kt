package com.innoveworkshop.gametest.assets

import android.graphics.Color
import android.os.Looper
import android.util.Log
import com.innoveworkshop.gametest.MainActivity
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Physics
import com.innoveworkshop.gametest.engine.Vector

class PlinkoBall(
    xPos: Float,
    val weigth: Float,
    val listOfObs: MutableList<Circle?>,
    val listOfMult: MutableList<Multiplier?>
): Circle(xPos, 20f, 35f, Color.RED, Vector(0f,0f)){
    var time = 0f
    var deltaTime = 0.016f*2
    var initialY = 0f
    var initialX = 0f
    var initialSpeedX = 0f
    var initialSpeedY = 1000f
    var grav = 98.1f *5
    var windRes = 0f
    var gravForce = 0f
    var mula = 0f
    var mulaCounted = 0

    override fun onStart(surface: GameSurface?) {
        super.onStart(surface)

        if (position != null) {
            initialY = position.y
            initialX = position.x
            gravForce = weigth*grav
        }
    }

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        if(speed.x > 0){

        }

        if (isFloored == false) {
            position.y = Physics().ChangeY(
                initialY,
                initialSpeedY,
                grav,
                time,
            )
            position.x = Physics().ChangeX(
                initialX,
                initialSpeedX,
                windRes,
                time
            )
            time += deltaTime

        } else {
            CheckMult(listOfMult)
            destroy()
        }
        speed.y = Physics().GetVelocityY(initialSpeedY, time, grav)
        speed.x = Physics().GetVelocityX(initialSpeedX, time, windRes)
        var befSpeedX = this.speed.x
        var befSpeedY = this.speed.y

        if (time >= 3*deltaTime) {
            this.speed = Physics().Collisions(this,gravForce, weigth, listOfObs)
            this.speed.x = Physics().CollisionWithWalls(this, gameSurface)
        }
            if (time >= 3*deltaTime) {
                time = 0f
                initialSpeedX = speed.x
                initialSpeedY = speed.y
                initialY = this.position.y
                initialX = this.position.x
            }
    }

    fun CheckMult(listOfMult: MutableList<Multiplier?>){
        var i = 0
        while (i < listOfMult!!.size ){
            if (this.position.x < listOfMult[i]!!.position.x + listOfMult[i]!!.width/2 && this.position.x > listOfMult[i]!!.position.x - listOfMult[i]!!.width/2){
                mula = 5f * listOfMult[i]!!.multiplier
                mulaCounted = 1
            }
            i++
        }
    }
}