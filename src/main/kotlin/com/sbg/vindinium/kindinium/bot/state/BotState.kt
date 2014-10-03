package com.sbg.vindinium.kindinium.bot.state

import com.sbg.vindinium.kindinium.model.Response
import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.bot.Action

trait BotState {
    fun chooseAction(response: Response, metaboard: MetaBoard): Action
}