package net.rolodophone.leftright.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PlayerComponent: Component, Pool.Poolable {
	companion object {
		val mapper = mapperFor<PlayerComponent>()
		const val LANE_SWITCH_SPEED = 675f
	}

	var fuel = 50f
	var distance = 0f
	var coins = 0

	override fun reset() {
		fuel = 50f
		distance = 0f
		coins = 0
	}
}