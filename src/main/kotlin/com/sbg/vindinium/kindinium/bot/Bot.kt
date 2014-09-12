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
import com.sbg.vindinium.kindinium.model.MetaBoard

enum class Action(val name: String) {
    NORTH: Action("North")
    EAST: Action("East")
    SOUTH: Action("South")
    WEST: Action("West")
    STAY: Action("Stay")
}

/**
 * A bot is any entity capable of choosing an action based in part on the
 * current state of game.
 */
trait Bot {
    fun chooseAction(game: Game, metaBoard: MetaBoard): Action
}