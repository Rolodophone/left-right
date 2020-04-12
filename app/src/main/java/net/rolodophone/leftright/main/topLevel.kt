package net.rolodophone.leftright.main

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import java.util.*


var appOpen = true
var width = 0f
var height = 0f
var halfWidth = 0f
var halfHeight = 0f
var wUnit = 0f
var hUnit = 0f
var statusBarHeight = 0

const val isDebug = true

var fps = Float.POSITIVE_INFINITY
var canvas = Canvas()

var whitePaint = Paint()
var bitmapPaint = Paint()
var dimmerPaint = Paint()


fun RectF.scaled(factor: Float): RectF {
    val diffHorizontal = (right - left) * (factor - 1f)
    val diffVertical = (bottom - top) * (factor - 1f)

    val mTop = top - diffVertical / 2f
    val mBottom = bottom + diffVertical / 2f

    val mLeft = left - diffHorizontal / 2f
    val mRight = right + diffHorizontal / 2f

    return RectF(mLeft, mTop, mRight, mBottom)
}


fun RectF.isOffscreen() = right < 0f || bottom < 0f || left > width || top > height


fun w(n: Int): Float = wUnit * n
fun w(n: Float): Float = wUnit * n
fun h(n: Int): Float = hUnit * n
fun h(n: Float): Float = hUnit * n

val randomGen = Random()

fun gaussianRandomFloat(mean: Float, std: Float) = randomGen.nextGaussian().toFloat() * std + mean
fun gaussianRandomInt(mean: Float, std: Float) = (randomGen.nextGaussian() * std + mean).toInt()

fun randomFloat(min: Float, max: Float) = (randomGen.nextFloat()) * (max-min) + min