package com.innoveworkshop.gametest.assets

import android.graphics.Color
import android.util.Log
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Physics
import com.innoveworkshop.gametest.engine.Vector

class PlinkoBall(
    xPos: Float,
    val listOfObs: MutableList<Circle?>
): Circle(xPos, 20f, 35f, Color.GRAY, Vector(0f,0f)){
    var time = 0f
    var deltaTime = 0.166666666f
    var InitialY = 0f
    var InitialX = 0f

    override fun onStart(surface: GameSurface?) {
        super.onStart(surface)

        if (position != null) {
            InitialY = position.y
            InitialX = position.x
        }
    }

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        if (isFloored == false) {
            position.y = Physics().ChangeY(
                InitialY,
                speed.y,
                9.81f,
                time,
            )
            position.x = Physics().ChangeX(
                InitialX,
                speed.x,
                0f,
                time
            )
            time += deltaTime

        } else {
            time = 0f
        }
        speed.y = Physics().GetVelocityY(speed.y, time)
        var befSpeed = this.speed
        this.speed = Physics().Collisions(this, listOfObs)
        if(befSpeed == this.speed){
            time = 0f
        }
    }
}