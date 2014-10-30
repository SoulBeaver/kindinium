package com.sbg.vindinium.kindinium.bot.state

import com.sbg.vindinium.kindinium.bot.Bot
import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.model.Response
import com.sbg.vindinium.kindinium.bot.Action
import com.sbg.vindinium.kindinium.model.board.Path

class AttackNearestHero(val bot: Bot): BotState {

    var pathToNearestHero: Path? = null

    override fun chooseAction(response: Response, metaboard: MetaBoard): Action {

        if (response.hero.life <= 30) {
            val state = GoingToTavern(bot)
            bot.switchToState(state)
            return state.chooseAction(response, metaboard)
        }

        if (pathToNearestHero == null)
            pathToNearestHero = metaboard.nearestHero(response.hero.pos)

        if (pathToNearestHero!!.size() > 2) {
            val state = CapturingNearestMine(bot)
            bot.switchToState(state)
            return state.chooseAction(response, metaboard)
        }

        val (_, action) = pathToNearestHero!!.next()

        if (pathToNearestHero!!.isEmpty()) {
            pathToNearestHero = null
        }

        return action
    }
}