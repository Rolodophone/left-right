package net.rolodophone.leftright.screen

import com.badlogic.gdx.graphics.Texture
import ktx.ashley.entity
import ktx.ashley.with
import net.rolodophone.leftright.LeftRight
import net.rolodophone.leftright.ecs.component.GraphicsComponent
import net.rolodophone.leftright.ecs.component.TransformComponent

class GameScreen(game: LeftRight) : LeftRightScreen(game) {
	private val playerTexture = Texture("graphics/car1.png")

	override fun show() {
		engine.entity {
			with<TransformComponent> {
				rect.setPosition(50f, 50f)
				setSizeFromTexture(playerTexture)
			}
			with<GraphicsComponent> {
				sprite.run {
					setRegion(playerTexture)
				}
			}
		}
	}

	override fun render(delta: Float) {
		engine.update(delta)
	}

	override fun dispose() {
		playerTexture.dispose()
	}
}