package com.sbg.vindinium.kindinium.bot

import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.model.Game
import com.sbg.vindinium.kindinium.model.Response
import kotlin.properties.Delegates
import com.sbg.vindinium.kindinium.model.board.BoardTile
import com.sbg.vindinium.kindinium.model.board.toBoardTile

class CompetitiveBot: Bot {
    private var heroTile: BoardTile by Delegates.notNull()

    override fun initialize(response: Response) {
        heroTile = toBoardTile("@${response.hero.id}") // e.g. @1
    }

    override fun chooseAction(response: Response, metaboard: MetaBoard): Action {

        // The only winning move is not to play.
        return Action.Stay
    }
}