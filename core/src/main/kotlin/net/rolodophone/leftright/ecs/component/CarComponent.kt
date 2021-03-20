package net.rolodophone.leftright.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class CarComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<CarComponent>()
	}

	var velocity = 0f
	var acceleration = 0f
	var spinSpeed = 0f
	var isSkidding = false

	override fun reset() {
		velocity = 0f
		acceleration = 0f
		spinSpeed = 0f
		isSkidding = false
	}
}