package com.sbg.vindinium.kindinium.model.board

import java.util.HashMap
import java.util.ArrayList
import sun.reflect.generics.reflectiveObjects.NotImplementedException
import java.util.Queue
import java.util.PriorityQueue
import com.sbg.vindinium.kindinium.model.Position

/**
 * The Meta Board represents the board in greater detail, revealing the position of many unique
 * objects on the map and routes to each object from an arbitrary position.
 */
data class MetaBoard(val board: Board) {
    private val boardNavigator: BoardNavigator
    private val boardLayout: Map<Position, BoardTile>
    private val heroes: Map<BoardTile, Position>
    private val mines: List<Position>
    private val contestedMines: Map<BoardTile, List<Position>>
    private val neutralMines: List<Position>
    private val taverns: List<Position>

    fun boardLayout(): Map<Position, BoardTile> = boardLayout

    fun hero(symbol: String): Position = heroes[toBoardTile(symbol)]!!
    fun hero(boardTile: BoardTile): Position = heroes[boardTile]!!

    fun mines(): List<Position> = mines
    fun minesOwnedBy(symbol: String): List<Position> = contestedMines[toBoardTile(symbol)]!!
    fun minesOwnedBy(boardTile: BoardTile): List<Position> = contestedMines[boardTile]!!
    fun neutralMines(): List<Position> = neutralMines

    fun taverns(): List<Position> = taverns

    fun boardSize() = board.size

    /* Implementation details start here */

    {
        boardLayout = parseMap()
        heroes = locateHeroes()
        contestedMines = locateContestedMines()
        neutralMines = locateNeutralMines()
        mines = neutralMines + contestedMines.values().reduce { flatList, list -> flatList + list }
        taverns = locateTaverns()

        boardNavigator = BoardNavigator(this)
    }

    private fun parseMap(): Map<Position, BoardTile> {
        val tiles = arrayListOf<Tile>()
        board.tiles.reader.use {
            var leftHalf = it.read()
            var rightHalf = it.read()

            while (leftHalf != -1 && rightHalf != -1) {
                tiles.add(Tile(leftHalf.toChar(), rightHalf.toChar()))

                leftHalf = it.read()
                rightHalf = it.read()
            }
        }

        return tiles.withIndices().map { pair ->
            val (i, tile) = pair

            Position(i / board.size, i % board.size) to toBoardTile(tile.toString())
        }.toMap()
    }

    private fun locateHeroes(): Map<BoardTile, Position> {
        return boardLayout.entrySet().filter { entry ->
            val boardTile = entry.value

            boardTile == BoardTile.HERO_ONE ||
            boardTile == BoardTile.HERO_TWO ||
            boardTile == BoardTile.HERO_THREE ||
            boardTile == BoardTile.HERO_FOUR
        }.map { entry -> entry.value to entry.key }.toMap()
    }

    private fun locateContestedMines(): Map<BoardTile, List<Position>> {
        val contestedMines = boardLayout.entrySet().filter { entry ->
            val boardTile = entry.value

            boardTile == BoardTile.HERO_ONE_GOLD_MINE ||
            boardTile == BoardTile.HERO_TWO_GOLD_MINE ||
            boardTile == BoardTile.HERO_THREE_GOLD_MINE ||
            boardTile == BoardTile.HERO_FOUR_GOLD_MINE
        }

        val truncatedContestedMines = hashMapOf(
                BoardTile.HERO_ONE to arrayListOf<Position>(),
                BoardTile.HERO_TWO to arrayListOf<Position>(),
                BoardTile.HERO_THREE to arrayListOf<Position>(),
                BoardTile.HERO_FOUR to arrayListOf<Position>()
        )
        for (mine in contestedMines) {
            val heroTile = toBoardTile(mine.value.symbol.replace('$', '@'))
            truncatedContestedMines[heroTile]!!.add(mine.key)
        }

        return truncatedContestedMines
    }

    private fun locateNeutralMines(): List<Position> {
        return boardLayout.entrySet().filter { entry ->
            val boardTile = entry.value

            boardTile == BoardTile.NEUTRAL_GOLD_MINE
        }.map { entry -> entry.key }
    }

    private fun locateTaverns(): List<Position> {
        return boardLayout.entrySet().filter { entry ->
            val boardTile = entry.value

            boardTile == BoardTile.TAVERN
        }.map { entry -> entry.key }
    }

    private data class Tile(val leftPiece: Char, val rightPiece: Char) {
        override fun toString(): String = "$leftPiece$rightPiece"
    }

    override fun toString(): String {
        return StringBuilder() {
            val boardLayoutValues = boardLayout.values()

            for ((i, tile) in boardLayoutValues.withIndices()) {
                if (i % board.size == 0)
                    appendln()
                append(tile.symbol)
            }
        }.toString()
    }
}