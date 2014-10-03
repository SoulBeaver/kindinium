package com.sbg.vindinium.kindinium.bot.state

import com.sbg.vindinium.kindinium.bot.Bot
import com.sbg.vindinium.kindinium.model.Response
import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.bot.Action
import com.sbg.vindinium.kindinium.model.board.Path
import kotlin.properties.Delegates

class CapturingNearestNeutralMine(val bot: Bot): BotState {
    private var pathToNearestNeutralMine: Path? = null

    override fun chooseAction(response: Response, metaboard: MetaBoard): Action {
        if (response.hero.life <= 20) {
            bot.switchToState(GoingToTavern(bot))
            return Action.Stay
        }

        if (pathToNearestNeutralMine == null) {
            if (metaboard.neutralMines().isEmpty()) {
                bot.switchToState(Waiting(bot))
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
}