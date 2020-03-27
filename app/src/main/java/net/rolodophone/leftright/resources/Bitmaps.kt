package net.rolodophone.leftright.resources

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import net.rolodophone.leftright.R

class Bitmaps(ctx: Context) {
    private val bitmapOptions = BitmapFactory.Options()
    init {
        bitmapOptions.inScaled = false
    }

    val car1: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car1, bitmapOptions)
    val car1Hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car1_hit, bitmapOptions)
    val car2: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car2, bitmapOptions)
    val car2Hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car2_hit, bitmapOptions)
    val car3: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car3, bitmapOptions)
    val car3Hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car3_hit, bitmapOptions)
    val car4: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car4, bitmapOptions)
    val car4Hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car4_hit, bitmapOptions)
    val car5: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car5, bitmapOptions)
    val car5Hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car5_hit, bitmapOptions)
    val car6: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car6, bitmapOptions)
    val car6Hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car6_hit, bitmapOptions)
    val deathMsg: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.death_msg, bitmapOptions)
    val fuel: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.fuel, bitmapOptions)
    val mainMenu: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.main_menu, bitmapOptions)
    val playAgain: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.play_again, bitmapOptions)
    val cone: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.cone, bitmapOptions)
    val oil: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.oil, bitmapOptions)
    val coin: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.coin, bitmapOptions)
    val coinShining = listOf(
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin0, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin1, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin2, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin3, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin4, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin5, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin6, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin7, bitmapOptions)
    )
}