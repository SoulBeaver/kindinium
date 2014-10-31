package com.sbg.vindinium.kindinium.bot

import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.model.Game
import com.sbg.vindinium.kindinium.model.Response
import kotlin.properties.Delegates
import com.sbg.vindinium.kindinium.model.board.BoardTile
import com.sbg.vindinium.kindinium.model.board.toBoardTile
import com.sbg.vindinium.kindinium.model.board.Path
import org.slf4j.LoggerFactory
import com.sbg.vindinium.kindinium.bot.state.BotState
import com.sbg.vindinium.kindinium.bot.state.CapturingNearestMine
import com.sbg.vindinium.kindinium.bot.state.CapturingNearestNeutralMine

/**
 * The CompetitiveBot aims to provide a reasonably sophisticated AI suitable for
 * arena-mode play.
 */
class CompetitiveBot: Bot {
    private var log = LoggerFactory.getLogger(javaClass<CompetitiveBot>())!!

    private var heroTile: BoardTile by Delegates.notNull()
    private var botState: BotState by Delegates.notNull()

    override fun initialize(response: Response, metaboard: MetaBoard) {
        heroTile = metaboard[response.hero.spawnPos]

        botState = CapturingNearestNeutralMine(this)
    }

    override fun chooseAction(response: Response, metaboard: MetaBoard): Action {
        return botState.chooseAction(response, metaboard)
    }

    override fun switchToState(state: BotState) {
        botState = state
    }
}