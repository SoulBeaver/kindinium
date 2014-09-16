package com.sbg.vindinium.kindinium.bot

import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.model.Game
import com.sbg.vindinium.kindinium.model.Response
import kotlin.properties.Delegates
import com.sbg.vindinium.kindinium.model.board.BoardTile
import com.sbg.vindinium.kindinium.model.board.toBoardTile
import com.sbg.vindinium.kindinium.model.board.Path
import org.slf4j.LoggerFactory

/**
 * The CompetitiveBot aims to provide a reasonably sophisticated AI suitable for
 * arena-mode play.
 */
class CompetitiveBot: Bot {
    private var log = LoggerFactory.getLogger(javaClass<CompetitiveBot>())!!

    private var state = BotState.CapturingNearestNeutralMines

    private var heroTile: BoardTile by Delegates.notNull()
    private var response: Response by Delegates.notNull()
    private var metaboard: MetaBoard by Delegates.notNull()

    // BotState.CapturingNearestNeutralMine
    private var pathToNearestNeutralMine: Path? = null

    // BotState.GoingToTavern
    private var pathToNearestTavern: Path? = null

    override fun initialize(response: Response, metaboard: MetaBoard) {
        heroTile = metaboard[response.hero.spawnPos]
    }

    override fun chooseAction(response: Response, metaboard: MetaBoard): Action {
        this.response = response
        this.metaboard = metaboard

        when (state) {
            BotState.CapturingNearestNeutralMines ->
                    return captureNearestNeutralMine()
            BotState.GoingToTavern ->
                    return goToTavern()
            BotState.Waiting ->
                    return waiting()
            else -> throw IllegalStateException("This is not a valid state for the bot to be in:  $state")
        }
    }

    private fun captureNearestNeutralMine(): Action {
        if (response.hero.life <= 20) {
            state = BotState.GoingToTavern
            return Action.Stay
        }

        if (pathToNearestNeutralMine == null) {
            if (metaboard.neutralMines().isEmpty()) {
                state = BotState.Waiting
                return Action.Stay
            }

            pathToNearestNeutralMine = metaboard.nearestNeutralMine(response.hero.pos)
        }

        val (_, action) = pathToNearestNeutralMine!!.next()

        if (pathToNearestNeutralMine!!.isEmpty()) {
            pathToNearestNeutralMine = null
        }

        return action
    }

    private fun goToTavern(): Action {
        if (response.hero.life < 100) {
            state = BotState.CapturingNearestNeutralMines
            return Action.Stay
        }

        if (pathToNearestTavern == null)
            pathToNearestTavern = metaboard.nearestTavern(response.hero.pos)

        val (_, action) = pathToNearestTavern!!.next()

        if (pathToNearestTavern!!.isEmpty()) {
            pathToNearestTavern = null
        }

        return action
    }

    private fun waiting(): Action {
        if (response.hero.life <= 20) {
            state = BotState.GoingToTavern
            return Action.Stay
        } else if (metaboard.neutralMines().isNotEmpty()) {
            state = BotState.CapturingNearestNeutralMines
            return Action.Stay
        } else {
            return Action.Stay
        }
    }
}

private enum class BotState(val description: String) {
    CapturingNearestNeutralMines: BotState("capturing nearest neutral mines")
    Waiting: BotState("waiting")
    GoingToTavern: BotState("going to tavern")

    override fun toString(): String = description
}