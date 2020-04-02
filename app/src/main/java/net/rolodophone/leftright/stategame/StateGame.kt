package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.button.Button
import net.rolodophone.leftright.main.MainActivity
import net.rolodophone.leftright.main.State

class StateGame(override val ctx: MainActivity) : State {
    override val numThingsToLoad = 1

    enum class State {NONE, PAUSED, GAME_OVER}

    var state = State.NONE
        set(value) {
            when (value) {
                State.NONE -> {
                    ctx.music.resume()
                }
                State.PAUSED -> {
                    ctx.music.pause()
                }
                State.GAME_OVER -> {
                    ctx.music.pause()
                    gameOverOverlay.prepare()
                }
            }
            field = value
        }

    override val buttons = mutableListOf<Button.ButtonHandler>()

    val road = Road(ctx, this)
    val player = Player(ctx, this)
    val weather = Weather(ctx, this)
    val status = Status(ctx, this)
    val gameOverlay = GameOverlay(ctx, this)
    val pausedOverlay = PausedOverlay(ctx, this)
    val gameOverOverlay = GameOverOverlay(ctx, this)

    init {
        ctx.music.playGame()
    }

    override fun update() {
        status.update()
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
                status.draw()
                gameOverlay.draw()
            }
            State.PAUSED -> {
                status.draw()
                pausedOverlay.draw()
            }
            State.GAME_OVER -> {
                gameOverOverlay.draw()
            }
        }
    }
}