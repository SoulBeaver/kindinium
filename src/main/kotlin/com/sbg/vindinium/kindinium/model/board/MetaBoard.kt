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

    fun hero(symbol: String): Position = hero(toBoardTile(symbol))
    fun hero(boardTile: BoardTile): Position = importantTiles[boardTile]!!.single()

    fun neutralMines(): List<Position> = importantTiles[BoardTile.NEUTRAL_GOLD_MINE]!!

    fun minesOwnedBy(symbol: String): List<Position> = minesOwnedBy(toBoardTile(symbol.replace("@", "$")))
    fun minesOwnedBy(boardTile: BoardTile): List<Position> = importantTiles[boardTile]!!

    fun allMines(): List<Position> = neutralMines() + minesOwnedBy("@1") + minesOwnedBy("@2") + minesOwnedBy("@3") + minesOwnedBy("@4")

    fun taverns(): List<Position> = importantTiles[BoardTile.TAVERN]!!

    fun nearestNeutralMine(from: Position): Path? = boardNavigator.pathToNearest(neutralMines(), from)
    fun nearestMine(from: Position): Path = boardNavigator.pathToNearest(allMines(), from)!!

    fun nearestTavern(from: Position): Path = boardNavigator.pathToNearest(taverns(), from)!!

    fun pathTo(from: Position, to: Position): Path? = boardNavigator.pathTo(to, from)

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