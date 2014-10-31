package com.sbg.vindinium.kindinium.model.board

import java.util.HashMap
import java.util.ArrayList
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import java.util.Queue
import java.util.PriorityQueue
import com.sbg.vindinium.kindinium.model.Position
import com.sbg.vindinium.kindinium.paired
import java.util.SortedMap

/**
 * The Meta Board represents the board in greater detail, revealing the position of many unique
 * objects on the map and routes to each object from an arbitrary position.
 */
data class MetaBoard(val board: Board) {
    private val structuredMap: ArrayList<ArrayList<BoardTile>>
    private val importantTiles: Map<BoardTile, ArrayList<Position>>
    private val boardNavigator: BoardNavigator

    /**
     * Return the position of the specified hero.
     */
    fun hero(symbol: String): Position = hero(toBoardTile(symbol))
    fun hero(boardTile: BoardTile): Position = importantTiles[boardTile]!!.single()

    /**
     * Return the position of any neutral mine.
     */
    fun neutralMines(): List<Position> = importantTiles[BoardTile.NEUTRAL_GOLD_MINE]!!

    /**
     * Return the position of any mine owned by the specified hero.
     */
    fun minesOwnedBy(symbol: String): List<Position> = minesOwnedBy(toBoardTile(symbol.replace("@", "$")))
    fun minesOwnedBy(boardTile: BoardTile): List<Position> = importantTiles[boardTile]!!

    /**
     * Return the position of all mines.
     */
    fun allMines(): List<Position> = neutralMines() + minesOwnedBy("@1") + minesOwnedBy("@2") + minesOwnedBy("@3") + minesOwnedBy("@4")

    /**
     * Return the position of available mines
     */
    fun availableMines(enemies : Array<String>) : List<Position> {
        return neutralMines() + minesOwnedBy(enemies[0]) + minesOwnedBy(enemies[1]) + minesOwnedBy(enemies[2])
    }

    /**
     * Return the position of all heroes
     */
    fun heroes(): List<Position> {
        val heroesPositions = ArrayList<Position>()
        heroesPositions.addAll(importantTiles[BoardTile.HERO_ONE])
        heroesPositions.addAll(importantTiles[BoardTile.HERO_TWO])
        heroesPositions.addAll(importantTiles[BoardTile.HERO_THREE])
        heroesPositions.addAll(importantTiles[BoardTile.HERO_FOUR])
        return heroesPositions;
    }

    /**
     * Return the position of nearest hero
     */
    fun nearestHero(from: Position) : Path? = boardNavigator.pathToNearest(heroes(), from)

    /**
     * Return the position of all taverns.
     */
    fun taverns(): List<Position> = importantTiles[BoardTile.TAVERN]!!

    /**
     * Plots a path, if possible, to the nearest neutral mine.C
     */
    fun nearestNeutralMine(from: Position): Path? = boardNavigator.pathToNearest(neutralMines(), from)

    /**
     * Plots a path, if possible, to the nearest mine.
     */
    fun nearestMine(from: Position): Path? = boardNavigator.pathToNearest(allMines(), from)

    /**
     * Plots a path, if possible, to the nearest mine.
     */
    fun nearestAvailableMine(from: Position, enemies: Array<String>): Path? = boardNavigator.pathToNearest(availableMines(enemies), from)

    /**
     * Plots a path, if possible, to the nearest tavern.
     */
    fun nearestTavern(from: Position): Path? = boardNavigator.pathToNearest(taverns(), from)

    /**
     * Plots a path, if possible, from `from` to `to`.
     */
    fun pathTo(from: Position, to: Position): Path? = boardNavigator.pathTo(to, from)

    /**
     * Return the BoardTile at the given location.
     *
     * Alternative syntax:
     *
     *     metaboard[0, 0] // or metaboard[Position(0, 0)]
     */
    fun get(x: Int, y: Int): BoardTile {
        return get(Position(x, y))
    }
    fun get(position: Position): BoardTile {
        return structuredMap[position.y][position.x]
    }

    {
        structuredMap = ArrayList<ArrayList<BoardTile>>()
        importantTiles = hashMapOf(
                BoardTile.HERO_ONE to arrayListOf(),
                BoardTile.HERO_TWO to arrayListOf(),
                BoardTile.HERO_THREE to arrayListOf(),
                BoardTile.HERO_FOUR to arrayListOf(),

                BoardTile.TAVERN to arrayListOf(),

                BoardTile.NEUTRAL_GOLD_MINE to arrayListOf(),
                BoardTile.HERO_ONE_GOLD_MINE to arrayListOf(),
                BoardTile.HERO_TWO_GOLD_MINE to arrayListOf(),
                BoardTile.HERO_THREE_GOLD_MINE to arrayListOf(),
                BoardTile.HERO_FOUR_GOLD_MINE to arrayListOf(),

                BoardTile.ROAD to arrayListOf(),
                BoardTile.IMPASSABLE_WOOD to arrayListOf()
        )
        boardNavigator = BoardNavigator(this)

        var i = 0
        while (i != board.tiles.size) {
            if (i % (board.size * 2) == 0)
                structuredMap.add(arrayListOf())

            val left = board.tiles[i]
            val right = board.tiles[i + 1]

            structuredMap.last!!.add(toBoardTile("$left$right"))

            i += 2
        }

        for ((y, row) in structuredMap.withIndices()) {
            for ((x, tile) in row.withIndices()) {
                importantTiles[tile]!!.add(Position(x, y))
            }
        }
    }

    override fun toString(): String {
        return StringBuilder() {
            for (row in structuredMap)
                appendln(row.fold("") { reconstructed, part -> reconstructed + part })
        }.toString()
    }
}