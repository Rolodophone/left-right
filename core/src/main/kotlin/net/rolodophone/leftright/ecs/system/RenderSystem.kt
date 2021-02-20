package net.rolodophone.leftright.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.error
import ktx.log.logger
import net.rolodophone.leftright.ecs.component.GraphicsComponent
import net.rolodophone.leftright.ecs.component.TransformComponent

private val log = logger<RenderSystem>()

class RenderSystem(
	private val batch: Batch,
	private val gameViewport: Viewport
): SortedIteratingSystem(
	allOf(TransformComponent::class, GraphicsComponent::class).get(),
	compareBy { entity -> entity[TransformComponent.mapper] }
) {
	override fun update(deltaTime: Float) {
		forceSort()
		gameViewport.apply()
		batch.use(gameViewport.camera.combined) {
			super.update(deltaTime)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transform = entity[TransformComponent.mapper]
		requireNotNull(transform) { "Entity $entity must have a TransformComponent" }

		val graphics = entity[GraphicsComponent.mapper]
		requireNotNull(graphics) { "Entity $entity must have a GraphicsComponent" }

		if (graphics.sprite.texture == null) {
			log.error { "Entity $entity has no texture for rendering" }
			return
		}

		graphics.sprite.run {
			rotation = transform.rotationDeg
			setBounds(transform.position.x, transform.position.y, transform.size.x, transform.size.y)
			draw(batch)
		}
	}
}