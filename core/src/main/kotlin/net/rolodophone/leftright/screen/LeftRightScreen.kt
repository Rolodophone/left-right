package net.rolodophone.leftright.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxScreen
import net.rolodophone.leftright.GameTextures
import net.rolodophone.leftright.LeftRight

abstract class LeftRightScreen(
	val game: LeftRight,
	val batch: Batch = game.batch,
	val gameViewport: Viewport = game.gameViewport,
	val engine: Engine = game.engine,
	val textures: GameTextures = game.gameTextures
): KtxScreen {
	override fun resize(width: Int, height: Int) {
		gameViewport.update(width, height, true)
	}
}