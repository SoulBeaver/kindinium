package com.sbg.vindinium.kindinium.bot

import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.model.Game
import com.sbg.vindinium.kindinium.model.Response
import kotlin.properties.Delegates
import com.sbg.vindinium.kindinium.model.board.BoardTile
import com.sbg.vindinium.kindinium.model.board.toBoardTile
import com.sbg.vindinium.kindinium.model.board.Path
import org.slf4j.LoggerFactory

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
        heroTile = metaboard[response.hero.spawnPosition()]

        log.info("We are hero $heroTile and are at position ${response.hero.spawnPosition()}")
        log.info("I am currentl $state")
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
            log.info("Now I am $state")
            return Action.Stay
        }

        if (pathToNearestNeutralMine == null) {
            if (metaboard.neutralMines().isEmpty()) {
                state = BotState.Waiting
                log.info("Now I am $state")
                return Action.Stay
            }

            pathToNearestNeutralMine = metaboard.nearestNeutralMine(response.hero.position())
            log.info("I am capturing the mine at ${pathToNearestNeutralMine!!.destination}")
        }

        val (nextPosition, action) = pathToNearestNeutralMine!!.next()

        log.info("I am going $action to $nextPosition")

        if (pathToNearestNeutralMine!!.isEmpty()) {
            log.info("I have arrived at the mine.")
            pathToNearestNeutralMine = null
        }

        return action
    }

    private fun goToTavern(): Action {
        if (response.hero.life > 20) {
            state = BotState.CapturingNearestNeutralMines
            log.info("Now I am $state")
            return Action.Stay
        }

        if (pathToNearestTavern == null)
            pathToNearestTavern = metaboard.nearestTavern(response.hero.position())

        val (nextPosition, action) = pathToNearestTavern!!.next()

        log.info("I am going $action to $nextPosition")

        if (pathToNearestTavern!!.isEmpty()) {
            log.info("I have arrived at the tavern.")
            pathToNearestTavern = null
        }

        return action
    }

    private fun waiting(): Action {
        if (response.hero.life <= 20) {
            state = BotState.GoingToTavern
            log.info("Now I am $state")
            return Action.Stay
        } else if (metaboard.neutralMines().isNotEmpty()) {
            state = BotState.CapturingNearestNeutralMines
            log.info("Now I am $state")
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