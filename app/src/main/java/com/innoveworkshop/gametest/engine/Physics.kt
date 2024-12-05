package com.innoveworkshop.gametest.engine

import android.util.Log
import androidx.collection.floatObjectMapOf
import kotlin.concurrent.fixedRateTimer
import kotlin.math.sqrt

class Physics {


    fun ChangeY(
        initialYPosition: Float,
        initialYVelocity: Float,
        aceleration: Float,
        time: Float,
    ): Float{
        //Log.d("TIME", time.toString())
        val tempY = initialYPosition + (initialYVelocity * time) + (aceleration) * (time*time)
        //Log.d("Y: ", tempY.toString())
        return tempY
    }

    fun ChangeX(
        initialXPosition: Float,
        initialXVelocity: Float,
        aceleration: Float,
        time: Float,
    ): Float{
        //Log.d("TIME", time.toString())
        val tempX = initialXPosition + (initialXVelocity * time) + (aceleration) * (time*time)
        //Log.d("X: ", tempX.toString())
        return tempX
    }
    fun GetVelocityY(initial:Float, time:Float, grav:Float): Float {
        val velocity = (initial + (grav*time))
        return velocity
    }
    fun GetVelocityX(initial:Float, time:Float, aceleration:Float): Float {
        val velocity = (initial + (aceleration*time))
        return velocity
    }

    fun Collisions(
        mainObject: Circle,
        gravForce: Float,
        mass:Float,
        objectsToCollide: MutableList<Circle?>
    ): Vector {
        for(Circle in objectsToCollide){
            var distanceX = mainObject.position.x - Circle!!.position.x
            var distanceY = mainObject.position.y - Circle.position.y
            var distance = sqrt((distanceX * distanceX) + (distanceY * distanceY))
            if(distance <= (Circle.radius + mainObject.radius)){
                var mainObjectSpeed = sqrt((mainObject.speed.x*mainObject.speed.x)+(mainObject.speed.y*mainObject.speed.y))
                var velocityX = (distanceX/distance)*mainObjectSpeed
                var velocityY = (distanceY/distance)*mainObjectSpeed
                var velocity = Vector(velocityX/2, velocityY/2)
                return velocity
            }
        }
        return Vector(mainObject.speed.x,mainObject.speed.y)
    }
    fun CollisionWithWalls(
        mainObject: Circle,
        surface: GameSurface?
    ):Float{
        if(mainObject.position.x + mainObject.radius >= surface!!.width || mainObject.position.x - mainObject.radius <= 0){
            return -mainObject.speed.x
        }
        return mainObject.speed.x
    }
}