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

data class Hero {
    /**
     * The hero's id for the current game.
     */
    val id = 1

    /**
     * The hero's name.
     */
    val name = ""

    /**
     * The player's generated id.
     */
    val userId = ""

    /**
     * The player's rating. Starts at 1200
     */
    val elo = 1200

    /**
     * The hero's current position
     */
    val pos = Position(2, 4)
        get() = Position($pos.y, $pos.x)

    /**
     * The hero's current life pool. 0 is a dead hero, but life cannot exceed 100.
     */
    val life = 100

    /**
     * The hero's current gold pool.
     */
    val gold = 0

    /**
     * The number of mines under the hero's control.
     */
    val mineCount = 0

    /**
     * Where the player spawns on death. On initialization, is the same as the position.
     */
    val spawnPos = Position(2, 4)
        get() = Position($spawnPos.y, $spawnPos.x)

    /**
     * Whether or not the hero took more than the one second time to process.
     */
    val crashed = false
}