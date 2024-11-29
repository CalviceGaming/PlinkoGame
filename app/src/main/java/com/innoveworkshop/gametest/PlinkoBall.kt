package com.innoveworkshop.gametest

import android.graphics.Color
import com.innoveworkshop.gametest.engine.Circle
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Physics
import com.innoveworkshop.gametest.engine.Vector

class PlinkoBall(
    xPos: Float
): Circle(xPos, 20f, 35f, Color.BLACK){
    var time = 0f
    var deltaTime = 0.016f
    var InitialY = 0f

    override fun onStart(surface: GameSurface?) {
        super.onStart(surface)

        if (position != null) {
            InitialY = position.y
        }
    }

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        if (isFloored == false) {
            position.y = Physics().ChnageY(
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