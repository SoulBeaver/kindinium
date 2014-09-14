package com.sbg.vindinium.kindinium.model

import org.spek.Spek
import com.google.gson.Gson
import com.sbg.vindinium.kindinium.streamToString
import kotlin.test.assertEquals
import com.sbg.vindinium.kindinium.model.board.Board
import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.model.board.BoardTile

class MetaBoardTest: Spek() {{
    val gson = Gson()
    val json = javaClass.getClassLoader()!!.getResourceAsStream("board.json")!!.use { streamToString(it) }
    val board = gson.fromJson(json, javaClass<Board>())!!

    given("A MetaBoard") {
        val metaboard = MetaBoard(board)

        on("asking for the location of hero @1") {
            it("returns the position (4, 2)") {
                assertEquals(Position(4, 2), metaboard.hero("@1"))
            }
        }

        on("asking for the location of hero @2") {
            it("returns the position (17, 2)") {
                assertEquals(Position(17, 2), metaboard.hero("@2"))
            }
        }

        on("asking for the position of hero @3") {
            it("returns the position (17, 20)") {
                assertEquals(Position(17, 19), metaboard.hero("@3"))
            }
        }

        on("asking for the position of hero @4") {
            it("returns the position (4, 20)") {
                assertEquals(Position(4, 19), metaboard.hero("@4"))
            }
        }

        on("asking for mines owned by @1") {
            it("returns an empty list") {
                assertEquals(listOf<Position>(), metaboard.minesOwnedBy("@1"))
            }
        }

        on("asking for mines owned by @2") {
            it("returns a list with positions (1, 10) and (1, 11)") {
                assertEquals(listOf(Position(1, 10), Position(1, 11)), metaboard.minesOwnedBy("@2"))
            }
        }

        on("asking for mines owned by @3") {
            it("returns a list with a single position (13, 8)") {
                assertEquals(listOf(Position(13, 8)), metaboard.minesOwnedBy("@3"))
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

                assertEquals(neutralMinePositions, metaboard.neutralMines())
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

                assertEquals(tavernPositions, metaboard.taverns())
            }
        }

        /*
        on("asking for the nearest neutral mine from @1") {
            it("returns the mine at (7, 2)") {
                val hero1Position = metaboard.hero("@1")

                assertEquals(Position(7, 2), metaboard.nearestMine(hero1Position))
            }
        }

        on("asking for the nearest neutral mine from @2") {
            it("returns the mine at (14, 2)") {
                val hero2Position = metaboard.heroes[BoardTile.HERO_TWO]!!

                assertEquals(Position(14, 2), metaboard.nearestMine(hero2Position))
            }
        }

        on("asking for the nearest tavern from @1") {
            it("returns the tavern (3, 8)") {
                val hero1Position = metaboard.heroes[BoardTile.HERO_ONE]!!

                assertEquals(Position(3, 8), metaboard.nearestTavern(hero1Position))
            }
        }

        on("asking for the nearest hero from @1") {
            it("returns the hero @2 at (17, 4)") {
                val hero1Position = metaboard.heroes[BoardTile.HERO_ONE]!!
                val hero2Position = metaboard.heroes[BoardTile.HERO_TWO]!!

                assertEquals(hero2Position, metaboard.nearestHero(hero1Position))
            }
        }
        */
    }
}}