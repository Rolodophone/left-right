package net.rolodophone.leftright.resources

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import net.rolodophone.leftright.R
import net.rolodophone.leftright.main.MainActivity

class Bitmaps(private val ctx: MainActivity) {
    private val bitmapOptions = BitmapFactory.Options()
    init {
        bitmapOptions.inScaled = false
    }

    val car1 = Car1()
    val car2 = Car2()
    val car3 = Car3()
    val car4 = Car4()
    val car5 = Car5()
    val car6 = Car6()

    interface Car { val clean: Bitmap; val hit: Bitmap }

    inner class Car1 : Car {
        override val clean: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car1, bitmapOptions)
        override val hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car1_hit, bitmapOptions)
    }
    inner class Car2 : Car {
        override val clean: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car2, bitmapOptions)
        override val hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car2_hit, bitmapOptions)
    }
    inner class Car3 : Car {
        override val clean: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car3, bitmapOptions)
        override val hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car3_hit, bitmapOptions)
    }
    inner class Car4 : Car {
        override val clean: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car4, bitmapOptions)
        override val hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car4_hit, bitmapOptions)
    }
    inner class Car5 : Car {
        override val clean: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car5, bitmapOptions)
        override val hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car5_hit, bitmapOptions)
    }
    inner class Car6 : Car {
        override val clean: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car6, bitmapOptions)
        override val hit: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.car6_hit, bitmapOptions)
    }

    val deathMsg: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.death_msg, bitmapOptions)
    val fuel: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.fuel, bitmapOptions)
    val mainMenu: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.main_menu, bitmapOptions)
    val playAgain: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.play_again, bitmapOptions)
    val cone: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.cone, bitmapOptions)
    val oil: Bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.oil, bitmapOptions)
    val coin = listOf(
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin1, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin2, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin3, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin4, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin5, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin6, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin7, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.coin8, bitmapOptions)
    )
    val camel = listOf(
        BitmapFactory.decodeResource(ctx.resources, R.drawable.camel1, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.camel2, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.camel3, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.camel4, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.camel5, bitmapOptions),
        BitmapFactory.decodeResource(ctx.resources, R.drawable.camel6, bitmapOptions)
    )
}