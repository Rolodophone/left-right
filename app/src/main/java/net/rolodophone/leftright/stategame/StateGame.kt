package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.main.MainActivity
import net.rolodophone.leftright.main.State

class StateGame(override val ctx: MainActivity) : State {
    enum class State {NONE, PAUSED, GAME_OVER}

    var state = State.NONE

    override val buttons = mutableListOf<Button.ButtonHandler>()

    val road = Road(ctx, this)
    val player = Player(ctx, this)
    val weather = Weather(ctx, this)
    val statusBar = StatusBar(ctx, this)
    val gameOverlay = GameOverlay(ctx, this)
    val pausedOverlay = PausedOverlay(ctx, this)
    val gameOverOverlay = GameOverOverlay(ctx, this)

    init {
        ctx.music.playGame()
    }

    override fun update() {
        statusBar.update()
        gameOverlay.update()
        pausedOverlay.update()
        gameOverOverlay.update()

        when (state) {
            State.NONE -> {
                road.update()
                player.update()
            }
            State.PAUSED -> {}
            State.GAME_OVER -> {
                gameOverOverlay.updateMsg()
            }
        }
    }

    override fun draw() {
        road.draw()
        player.draw()
        weather.draw()

        when (state) {
            State.NONE -> {
                statusBar.draw()
                gameOverlay.draw()
            }
            State.PAUSED -> {
                statusBar.draw()
                pausedOverlay.draw()
            }
            State.GAME_OVER -> {
                gameOverOverlay.draw()
            }
        }
    }
}