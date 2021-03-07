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
				setSizeFromTexture(playerTexture)
				rect.setCenter(gameViewport.worldWidth / 2f, 0f)
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