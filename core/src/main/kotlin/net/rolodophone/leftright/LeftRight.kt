package net.rolodophone.leftright

import ktx.app.KtxGame
import ktx.app.KtxScreen

/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class LeftRight : KtxGame<KtxScreen>() {
	override fun create() {
		setScreen<FirstScreen>()
	}
}