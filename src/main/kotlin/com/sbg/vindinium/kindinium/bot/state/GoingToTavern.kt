package com.sbg.vindinium.kindinium.bot.state

import com.sbg.vindinium.kindinium.bot.Bot
import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.model.Response
import com.sbg.vindinium.kindinium.bot.Action
import com.sbg.vindinium.kindinium.model.board.Path

class GoingToTavern(val bot: Bot): BotState {
    var pathToNearestTavern: Path? = null

    override fun chooseAction(response: Response, metaboard: MetaBoard): Action {
        if (response.hero.life > 90) {
            val state = CapturingNearestMine(bot)
            bot.switchToState(state)
            return state.chooseAction(response, metaboard)
        }

        if (pathToNearestTavern == null)
            pathToNearestTavern = metaboard.nearestTavern(response.hero.pos)

        val (_, action) = pathToNearestTavern!!.next()

        if (pathToNearestTavern!!.isEmpty()) {
            pathToNearestTavern = null
        }

        return action
    }
}