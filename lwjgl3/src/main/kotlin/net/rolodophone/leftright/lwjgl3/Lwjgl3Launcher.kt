package net.rolodophone.leftright.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import net.rolodophone.leftright.LeftRight

/** Launches the desktop (LWJGL3) application.  */
fun main() {
	Lwjgl3Application(LeftRight(), Lwjgl3ApplicationConfiguration().apply {
		setTitle("LeftRight")
		setWindowedMode(9 * 60, 16 * 60)
		setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
	})
}