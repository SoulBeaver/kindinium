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

import kotlin.properties.Delegates

data class Board {
    /**
     * The size of a map. A map is guaranteed to be square, therefore the area of a map is size squared.
     */
    val size = 0

    /**
     * The textual, uninterpreted layout of the board. Each tile is two characters wide and the string contains
     * no line breaks. (@see http://vindinium.org/doc for more details)
     */
    val tiles = ""

    /**
     * The location of every hero.
     */
    fun heroes(): List<Position> {
        return listOf()
    }

    /**
     * A list of, if any, neutral mines on the map.
     */
    fun neutralMines(): List<Position> {
        return listOf()
    }

    /**
     * A list of, if any, mines owned by the hero with id heroId
     */
    fun minesOwnedBy(heroId: Int): List<Position> {
        return listOf()
    }

    /**
     * The location of every tavern.
     */
    fun taverns(): List<Position> {
        return listOf()
    }

    /**
     * The nearest mine relative to position
     */
    fun nearestMine(position: Position): Position {
        return Position(0, 0)
    }

    /**
     * The nearest neutral mine relative to position or null if none exist
     */
    fun nearestNeutralMine(position: Position): Position? {
        return Position(0, 0)
    }

    /**
     * The nearest hero relative to position
     */
    fun nearestHero(position: Position): Position {
        return Position(0, 0)
    }

    /**
     * The nearest tavern relative to position
     */
    fun nearestTavern(position: Position): Position {
        return Position(0, 0)
    }

    private fun parseTiles(tiles: String) {
        println("Parsing some new tiles!")
    }
}