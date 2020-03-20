package net.rolodophone.leftright.components

object buttons {
    val buttons = listOf(
        statusBar.debug.moreFuel,
        statusBar.debug.frenzyOn,
        statusBar.debug.frenzyOff,
        gameOverlay.pause,
        pausedOverlay.resume,
        pausedOverlay.btnShowDebug,
        pausedOverlay.btnHideDebug,
        gameOverOverlay.playAgain,
        gameOverOverlay.mainMenu,
        gameOverlay.leftButton,
        gameOverlay.rightButton
    )

    fun updateButtons() {
        for (button in buttons) button.update()
    }


}
