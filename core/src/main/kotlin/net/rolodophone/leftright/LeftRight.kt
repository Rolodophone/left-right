package net.rolodophone.leftright

import com.badlogic.gdx.Game

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class LeftRight : Game() {
	override fun create() {
		setScreen(FirstScreen())
	}
}