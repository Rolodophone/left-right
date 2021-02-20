package net.rolodophone.leftright.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import ktx.ashley.get

fun <T : Component> Entity.getNotNull(mapper: ComponentMapper<T>): T {
	val component = this[mapper]
	requireNotNull(component) { "Entity $this hasn't got the requested component" }
	return component
}