package net.rolodophone.leftright.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import net.rolodophone.leftright.ecs.component.CarComponent
import net.rolodophone.leftright.ecs.component.TransformComponent
import net.rolodophone.leftright.ecs.component.getNotNull

private const val DELTA_TIME = 0.04f

class MoveSystem: IteratingSystem(allOf(TransformComponent::class, CarComponent::class).get()) {
	private var accumulator = 0f

	override fun update(deltaTime: Float) {
		accumulator += deltaTime

		while (accumulator >= DELTA_TIME) {
			accumulator -= DELTA_TIME
			super.update(DELTA_TIME)
		}
	}

	override fun processEntity(entity: Entity, deltaTime: Float) {
		val transformComp = entity.getNotNull(TransformComponent.mapper)
		val carComp = entity.getNotNull(CarComponent.mapper)

		carComp.velocity += carComp.acceleration
		transformComp.rect.
	}
}