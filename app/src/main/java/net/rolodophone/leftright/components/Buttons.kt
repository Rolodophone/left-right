package net.rolodophone.leftright.components

import net.rolodophone.leftright.main.MainActivity

class Buttons(override val ctx: MainActivity) : Component {
    val buttons = listOf(
        ctx.statusBar.debug.moreFuel,
        ctx.statusBar.debug.frenzyOn,
        ctx.statusBar.debug.frenzyOff,
        ctx.gameOverlay.pause,
        ctx.pausedOverlay.resume,
        ctx.pausedOverlay.btnShowDebug,
        ctx.pausedOverlay.btnHideDebug,
        ctx.gameOverOverlay.playAgain,
        ctx.gameOverOverlay.mainMenu,
        ctx.gameOverlay.leftButton,
        ctx.gameOverlay.rightButton
    )

    fun updateButtons() {
        for (button in buttons) button.update()
    }


}
