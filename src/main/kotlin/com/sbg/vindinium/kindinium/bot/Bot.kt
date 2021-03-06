/*
Copyright 2014 Christian Broomfield

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.sbg.vindinium.kindinium.bot

import com.sbg.vindinium.kindinium.model.Game
import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.model.Response
import kotlin.properties.Delegates
import com.sbg.vindinium.kindinium.bot.state.BotState

/**
 * A bot is any entity capable of choosing an action based in part on the
 * current state of game.
 */
trait Bot {
    /**
     * Create or initialize the bot using the first server response.
     *
     * @param response The first response, or initial game state, from the server.
     * @param metaboard The MetaBoard representing the board.
     */
    fun initialize(response: Response, metaboard: MetaBoard)

    /**
     * Given the server's information and metaboard, choose an {@see Action} for the bot to take.
     */
    fun chooseAction(response: Response, metaboard: MetaBoard): Action

    /**
     * Change the bot's behavior through its BotState.
     */
    fun switchToState(state: BotState)
}