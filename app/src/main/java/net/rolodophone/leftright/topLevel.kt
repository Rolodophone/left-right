package net.rolodophone.leftright

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF


var appOpen = true
var width = 0f
var height = 0f
var halfWidth = 0f
var halfHeight = 0f
var wUnit = 0f
var hUnit = 0f
var statusBarHeight = 0

var state: State = stateGame
    set(value) {

        if (value == stateGameOver || value == stateGame && state != statePaused) {
            value.reset()
        }

        field = value
    }

var fps = Float.POSITIVE_INFINITY
var canvas = Canvas()

var whitePaint = Paint()
var dimmerPaint = Paint()

const val isDebug = true


fun centerOfLane(lane: Int): Float {
    require(lane < road.numLanes) { "lane index must be less than road.numLanes" }

    return (width * (lane + 1) - halfWidth) / (road.numLanes)
}

fun RectF.scale(factor: Float): RectF {
    val diffHorizontal = (right - left) * (factor - 1f)
    val diffVertical = (bottom - top) * (factor - 1f)

    val mTop = top - diffVertical / 2f
    val mBottom = bottom + diffVertical / 2f

    val mLeft = left - diffHorizontal / 2f
    val mRight = right + diffHorizontal / 2f

    return RectF(mLeft, mTop, mRight, mBottom)
}

fun randomChance(averageMetres: Int): Boolean {
    return (0 until ((averageMetres / player.ySpeedMps) * fps).toInt()).random() == 0
}


fun w(n: Int): Float = wUnit * n
fun w(n: Float): Float = wUnit * n
fun h(n: Int): Float = hUnit * n
fun h(n: Float): Float = hUnit * n