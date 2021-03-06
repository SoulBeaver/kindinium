package com.sbg.vindinium.kindinium.model.board

enum class BoardTile(val symbol: String) {
    ROAD: BoardTile("  ")
    IMPASSABLE_WOOD: BoardTile("##")

    HERO_ONE: BoardTile("@1")
    HERO_TWO: BoardTile("@2")
    HERO_THREE: BoardTile("@3")
    HERO_FOUR: BoardTile("@4")

    TAVERN: BoardTile("[]")

    NEUTRAL_GOLD_MINE: BoardTile("$-")

    HERO_ONE_GOLD_MINE: BoardTile("$1")
    HERO_TWO_GOLD_MINE: BoardTile("$2")
    HERO_THREE_GOLD_MINE: BoardTile("$3")
    HERO_FOUR_GOLD_MINE: BoardTile("$4")

    override fun toString(): String = symbol
}

fun toBoardTile(symbol: String): BoardTile {
    val boardTiles = BoardTile.values().toArrayList()

    return boardTiles.first {
        it.symbol == symbol
    }
}