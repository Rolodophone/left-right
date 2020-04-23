package net.rolodophone.leftright.stategame

import net.rolodophone.leftright.main.MainActivity
import net.rolodophone.leftright.main.State

class StateGame(ctx: MainActivity) : State(ctx) {
    override val numThingsToLoad = 1

    val bitmaps = ctx.bitmaps
    val sounds = ctx.sounds
    val music = ctx.music

    enum class State {NONE, PAUSED, GAME_OVER}

    var state = State.NONE
        set(value) {
            when (value) {
                State.NONE -> {
                    music.resume()
                }
                State.PAUSED -> {
                    music.pause()
                }
                State.GAME_OVER -> {
                    music.pause()
                    gameOverOverlay.prepare()
                }
            }
            field = value
        }

    val road = Road(this)
    val player = Player(this)
    val weather = Weather(this)
    val status = Status(this)
    val gameOverlay = GameOverlay(this)
    val pausedOverlay = PausedOverlay(this)
    val gameOverOverlay = GameOverOverlay(this)

    init {
        music.prepGame()
        road.objects.add(player)
    }

    override fun update() {
        status.update()
        gameOverlay.update()
        pausedOverlay.update()
        gameOverOverlay.update()

        if (state != State.PAUSED) {
            road.update()

            if (state == State.GAME_OVER) {
                gameOverOverlay.updateMsg()
            }
        }
    }

    override fun draw() {
        road.draw()
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