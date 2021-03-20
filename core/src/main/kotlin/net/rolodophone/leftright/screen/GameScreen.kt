package net.rolodophone.leftright.screen

import ktx.ashley.entity
import ktx.ashley.with
import net.rolodophone.leftright.LeftRight
import net.rolodophone.leftright.ecs.component.CarComponent
import net.rolodophone.leftright.ecs.component.GraphicsComponent
import net.rolodophone.leftright.ecs.component.TransformComponent

class GameScreen(game: LeftRight) : LeftRightScreen(game) {

	override fun show() {
		engine.entity {
			with<TransformComponent> {
				setSizeFromTexture(textures.car1)
				rect.setCenter(gameViewport.worldWidth / 2f, 0f)
			}
			with<GraphicsComponent> {
				sprite.setRegion(textures.car1)
			}
			with<CarComponent>()
		}
	}

	override fun render(delta: Float) {
		engine.update(delta)
	}

	override fun dispose() {
		textures.dispose()
	}
}