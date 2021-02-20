package net.rolodophone.leftright.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class GraphicsComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<GraphicsComponent>()
	}

	val sprite = Sprite()

	override fun reset() {
		sprite.texture = null
		sprite.setColor(1f, 1f, 1f, 1f)
	}
}