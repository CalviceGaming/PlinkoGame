package com.innoveworkshop.gametest.engine

import android.util.Log
import androidx.collection.floatObjectMapOf
import kotlin.concurrent.fixedRateTimer
import kotlin.math.sqrt

class Physics {
    private val gravity = 9.81f * 20

    fun ApplyForce(
        force: Float = 1f,
        mass: Float = 1f,
    ):Float {
        val gravityForce = mass * gravity
        Log.d("ACCELARATION", gravityForce.toString())
        val finalForce = gravityForce - force
        val accelaration = finalForce/mass
        return accelaration
    }

    fun ChangeY(
        initialYPosition: Float,
        initialYVelocity: Float,
        aceleration: Float,
        time: Float,
    ): Float{
        Log.d("TIME", time.toString())
        val tempY = initialYPosition + (initialYVelocity * time) + (aceleration) * (time*time)
        Log.d("Y: ", tempY.toString())
        return tempY
    }

    fun ChangeX(
        initialXPosition: Float,
        initialXVelocity: Float,
        aceleration: Float,
        time: Float,
    ): Float{
        Log.d("TIME", time.toString())
        val tempX = initialXPosition + (initialXVelocity * time) + (aceleration) * (time*time)
        Log.d("Y: ", tempX.toString())
        return tempX
    }
    fun GetVelocityY(inicial:Float, time:Float): Float {
        val velocity = (inicial + (9.81*time)).toFloat()
        return velocity
    }
    fun GetVelocityX(inicial:Float, time:Float, aceleration:Float): Float {
        val velocity = (inicial + (aceleration*time))
        return velocity
    }

    fun Collisions(
        mainObject: Circle,
        objectsToColide: MutableList<Circle?>
    ): Vector {
        for(Circle in objectsToColide){
            var distanceX = mainObject.position.x - Circle!!.position.x
            var distanceY = mainObject.position.y - Circle.position.y
            var distance = sqrt((distanceX * distanceX) + (distanceY * distanceY))
            if(distance <= (Circle.radius + mainObject.radius)){
                var velocityX = (distanceX/distance)*mainObject.speed.x
                var velocityY = (distanceY/distance)*mainObject.speed.y
                var velocity = Vector(velocityX, velocityY)
                return velocity
            }
        }
        return Vector(mainObject.speed.x,mainObject.speed.y)
    }
}