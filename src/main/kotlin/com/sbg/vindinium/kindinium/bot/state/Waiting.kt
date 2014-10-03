package com.sbg.vindinium.kindinium.bot.state

import com.sbg.vindinium.kindinium.bot.Bot
import com.sbg.vindinium.kindinium.bot.Action
import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.model.Response
import com.sbg.vindinium.kindinium.bot.state

class Waiting(val bot: Bot): BotState {
    override fun chooseAction(response: Response, metaboard: MetaBoard): Action {
        if (response.hero.life <= 20) {
            bot.switchToState(GoingToTavern(bot))
            return Action.Stay
        } else if (metaboard.neutralMines().isNotEmpty()) {
            bot.switchToState(CapturingNearestNeutralMine(bot))
            return Action.Stay
        } else {
            return Action.Stay
        }
    }
}