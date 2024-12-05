package com.innoveworkshop.gametest.assets

import android.graphics.Color
import android.icu.text.ListFormatter.Width
import android.icu.text.Transliterator.Position
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector

class Multiplier(
    position: Vector,
    width: Float,
    height: Float,
    colofOfMult: Int,
    multiplier: Float
):Rectangle(position, width, height, colofOfMult) {
    var multiplier = multiplier
}