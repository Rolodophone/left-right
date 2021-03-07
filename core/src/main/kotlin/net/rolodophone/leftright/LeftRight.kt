package net.rolodophone.leftright

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.log.debug
import ktx.log.logger
import net.rolodophone.leftright.ecs.system.RenderSystem
import net.rolodophone.leftright.screen.GameScreen
import net.rolodophone.leftright.screen.LeftRightScreen

private const val BATCH_SIZE = 1000

private val log = logger<LeftRight>()

class LeftRight : KtxGame<LeftRightScreen>() {
	val gameViewport = FitViewport(135f, 240f)
	val batch: Batch by lazy { SpriteBatch(BATCH_SIZE) }
	val gameTextures: GameTextures by lazy { GameTextures() }

	val engine: Engine by lazy { PooledEngine().apply {
		//addSystem(PlayerInputSystem(gameViewport))
		addSystem(RenderSystem(batch, gameViewport))
	} }

	override fun create() {
		Gdx.app.logLevel = LOG_DEBUG

		addScreen(GameScreen(this))
		setScreen<GameScreen>()
	}

	override fun dispose() {
		log.debug { "Disposing game" }

		super.dispose()

		log.debug {
			val sb = batch as SpriteBatch
			"Max sprites in batch: ${sb.maxSpritesInBatch}; size of batch: $BATCH_SIZE"
		}
		batch.dispose()
	}
}