package com.sbg.vindinium.kindinium.model

import org.spek.Spek
import com.google.gson.Gson
import com.sbg.vindinium.kindinium.streamToString
import kotlin.test.assertEquals

class MetaBoardTest: Spek() {{
    val gson = Gson()
    val json = javaClass.getClassLoader()!!.getResourceAsStream("board.json")!!.use { streamToString(it) }
    val board = gson.fromJson(json, javaClass<Board>())!!

    given("A MetaBoard") {
        val metaboard = MetaBoard(board)

        on("asking for the location of hero @1") {
            it("returns the position (4, 2)") {
                assertEquals(Position(4, 2), metaboard.heroes[BoardTile.HERO_ONE])
            }
        }

        on("asking for the location of hero @2") {
            it("returns the position (17, 2)") {
                assertEquals(Position(17, 2), metaboard.heroes[BoardTile.HERO_TWO])
            }
        }

        on("asking for the position of hero @3") {
            it("returns the position (17, 20)") {
                assertEquals(Position(17, 19), metaboard.heroes[BoardTile.HERO_THREE])
            }
        }

        on("asking for the position of hero @4") {
            it("returns the position (4, 20)") {
                assertEquals(Position(4, 19), metaboard.heroes[BoardTile.HERO_FOUR])
            }
        }

        on("asking for mines owned by @1") {
            it("returns an empty list") {
                assertEquals(listOf<Position>(), metaboard.contestedMines[BoardTile.HERO_ONE])
            }
        }

        on("asking for mines owned by @2") {
            it("returns a list with positions (1, 10) and (1, 11)") {
                assertEquals(listOf(Position(1, 10), Position(1, 11)), metaboard.contestedMines[BoardTile.HERO_TWO])
            }
        }

        on("asking for mines owned by @3") {
            it("returns a list with a single position (13, 8)") {
                assertEquals(listOf(Position(13, 8)), metaboard.contestedMines[BoardTile.HERO_THREE])
            }
        }

        on("asking for all neutral mines") {
            it("returns a list with a list with 9 mines") {
                val neutralMinePositions = listOf(
                        Position(7, 2),
                        Position(7, 19),
                        Position(8, 8),
                        Position(8, 13),
                        Position(13, 13),
                        Position(14, 2),
                        Position(14, 19),
                        Position(20, 10),
                        Position(20, 11)
                )

                assertEquals(neutralMinePositions, metaboard.neutralMines)
            }
        }

        on("asking for all taverns") {
            it("returns a list of 4 taverns") {
                val tavernPositions = listOf(
                        Position(3, 8),
                        Position(3, 13),
                        Position(18, 8),
                        Position(18, 13)
                )

                assertEquals(tavernPositions, metaboard.taverns)
            }
        }
    }
}}