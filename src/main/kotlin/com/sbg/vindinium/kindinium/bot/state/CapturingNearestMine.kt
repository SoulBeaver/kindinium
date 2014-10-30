package com.sbg.vindinium.kindinium.bot.state

import com.sbg.vindinium.kindinium.bot.Bot
import com.sbg.vindinium.kindinium.model.Response
import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.bot.Action
import com.sbg.vindinium.kindinium.model.board.Path
import kotlin.properties.Delegates
import com.sbg.vindinium.kindinium.model.getEnemies

class CapturingNearestMine(val bot: Bot): BotState {
    private var pathToNearestMine: Path? = null

    override fun chooseAction(response: Response, metaboard: MetaBoard): Action {
        if (response.hero.life <= 20) {
            val state = GoingToTavern(bot)
            bot.switchToState(state)
            return state.chooseAction(response, metaboard)
        }

        if (response.hero.life > 90) {
            val nearestHeroDistance = metaboard.nearestHero(response.hero.pos)?.size()
            if (nearestHeroDistance!! <=(2)) {
                val state = AttackNearestHero(bot)
                bot.switchToState(state)
                return state.chooseAction(response, metaboard)
            }
        }

        if (pathToNearestMine == null) {
            pathToNearestMine = metaboard.nearestAvailableMine(response.hero.pos, response.getEnemies())

            if (pathToNearestMine!!.isEmpty()) {
                if (metaboard.nearestTavern(response.hero.pos)?.size()!! > 2) {
                    val state = GoingToTavern(bot)
                    bot.switchToState(state)
                    return state.chooseAction(response, metaboard)
                } else {
                    bot.switchToState(Waiting(bot))
                    return Action.Stay
                }
            }
        }

        val (_, action) = pathToNearestMine!!.next()

        if (pathToNearestMine!!.isEmpty()) {
            pathToNearestMine = null
        }

        return action
    }
}