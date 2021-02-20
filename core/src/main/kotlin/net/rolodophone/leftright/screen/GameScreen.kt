package net.rolodophone.leftright.screen

import com.badlogic.gdx.graphics.Texture
import ktx.ashley.entity
import net.rolodophone.leftright.LeftRight

class GameScreen(game: LeftRight) : LeftRightScreen(game) {
	private val playerTexture = Texture("graphics/car1.png")

	override fun show() {
		engine.entity {

		}
	}

	override fun render(delta: Float) {
		engine.update(delta)
	}

	override fun dispose() {
		playerTexture.dispose()
	}
}