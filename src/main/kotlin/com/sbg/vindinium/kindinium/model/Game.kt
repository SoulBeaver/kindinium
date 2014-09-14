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
package com.sbg.vindinium.kindinium.model

import com.sbg.vindinium.kindinium
import com.sbg.vindinium.kindinium.model.board.Board

data class Game {
    /**
     * id for the game; with it one can view or play the game.
     */
    val id = ""

    /**
     * The current turn of the game
     */
    val turn = 0

    /**
     * The maximum amount of turns a game will take.
     */
    val maxTurns = 1200

    /**
     * A list of all four heroes. Crashed or disconnected heroes will still appear in the list.
     */
    val heroes = array<Hero>()

    /**
     * The board on which the Vindinium heroes fight.
     */
    val board = Board()

    /**
     * Whether or not the game has ended.
     */
    val finished = false
}