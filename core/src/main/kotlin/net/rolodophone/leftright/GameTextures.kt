package net.rolodophone.leftright

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.utils.Disposable

@Suppress("PropertyName", "unused")
class GameTextures: Disposable {
	private val atlas = TextureAtlas("graphics/game.atlas")

	val camel1				= atlas.findRegion("camel1				")!!
	val camel2				= atlas.findRegion("camel2				")!!
	val camel3				= atlas.findRegion("camel3				")!!
	val camel4				= atlas.findRegion("camel4				")!!
	val camel5				= atlas.findRegion("camel5				")!!
	val camel6				= atlas.findRegion("camel6				")!!
	val car1				= atlas.findRegion("car1				")!!
	val car1_hit			= atlas.findRegion("car1_hit			")!!
	val car2				= atlas.findRegion("car2				")!!
	val car2_hit			= atlas.findRegion("car2_hit			")!!
	val car3				= atlas.findRegion("car3				")!!
	val car3_hit			= atlas.findRegion("car3_hit			")!!
	val car4				= atlas.findRegion("car4				")!!
	val car4_hit			= atlas.findRegion("car4_hit			")!!
	val car5				= atlas.findRegion("car5				")!!
	val car5_hit			= atlas.findRegion("car5_hit			")!!
	val car6				= atlas.findRegion("car6				")!!
	val car6_hit			= atlas.findRegion("car6_hit			")!!
	val coin				= atlas.findRegion("coin				")!!
	val coin1				= atlas.findRegion("coin1				")!!
	val coin2				= atlas.findRegion("coin2				")!!
	val coin3				= atlas.findRegion("coin3				")!!
	val coin4				= atlas.findRegion("coin4				")!!
	val coin5				= atlas.findRegion("coin5				")!!
	val coin6				= atlas.findRegion("coin6				")!!
	val coin7				= atlas.findRegion("coin7				")!!
	val coin8				= atlas.findRegion("coin8				")!!
	val cone				= atlas.findRegion("cone				")!!
	val death_msg			= atlas.findRegion("death_msg			")!!
	val fuel				= atlas.findRegion("fuel				")!!
	val main_menu			= atlas.findRegion("main_menu			")!!
	val oil					= atlas.findRegion("oil					")!!
	val play_again			= atlas.findRegion("play_again			")!!

	override fun dispose() {
		atlas.dispose()
	}
}