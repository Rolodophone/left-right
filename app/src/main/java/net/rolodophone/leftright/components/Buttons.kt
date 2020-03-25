package net.rolodophone.leftright.components

import net.rolodophone.leftright.main.MainView.c

class Buttons {
    val buttons = listOf(
        c.statusBar.debug.moreFuel,
        c.statusBar.debug.frenzyOn,
        c.statusBar.debug.frenzyOff,
        c.gameOverlay.pause,
        c.pausedOverlay.resume,
        c.pausedOverlay.btnShowDebug,
        c.pausedOverlay.btnHideDebug,
        c.gameOverOverlay.playAgain,
        c.gameOverOverlay.mainMenu,
        c.gameOverlay.leftButton,
        c.gameOverlay.rightButton
    )

    fun updateButtons() {
        for (button in buttons) button.update()
    }


}
