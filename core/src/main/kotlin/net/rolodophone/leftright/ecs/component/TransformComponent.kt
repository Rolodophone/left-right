package net.rolodophone.leftright.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class TransformComponent: Component, Pool.Poolable, Comparable<TransformComponent> {
	companion object {
		val mapper = mapperFor<TransformComponent>()
	}

	val rect = Rectangle()
	var z = 0

	override fun reset() {
		//Note the rect is not reset. If you don't define the rect when using this component behaviour is undefined
		z = 0
	}

	override fun compareTo(other: TransformComponent): Int {
		return z - other.z
	}

	fun setSizeFromTexture(texture: Texture) {
		rect.setSize(texture.width.toFloat(), texture.height.toFloat())
	}
}