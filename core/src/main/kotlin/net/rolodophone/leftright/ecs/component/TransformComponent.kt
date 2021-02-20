package net.rolodophone.leftright.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class TransformComponent: Component, Pool.Poolable, Comparable<TransformComponent> {
	companion object {
		val mapper = mapperFor<TransformComponent>()
	}

	val position = Vector3()
	val size = Vector2()
	var rotationDeg = 0f

	override fun reset() {
		position.set(Vector3.Zero)
		size.set(1f, 1f)
		rotationDeg = 0f
	}

	override fun compareTo(other: TransformComponent): Int {
		return (position.z - other.position.z).toInt()
	}
}