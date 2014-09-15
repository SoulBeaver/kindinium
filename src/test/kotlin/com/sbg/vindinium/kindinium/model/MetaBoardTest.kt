package com.sbg.vindinium.kindinium.model

import org.spek.Spek
import com.google.gson.Gson
import com.sbg.vindinium.kindinium.streamToString
import kotlin.test.assertEquals
import com.sbg.vindinium.kindinium.model.board.Board
import com.sbg.vindinium.kindinium.model.board.MetaBoard
import com.sbg.vindinium.kindinium.model.board.BoardTile
import kotlin.test.assertTrue
import com.sbg.vindinium.kindinium.bot.Action

class MetaBoardTest: Spek() {{
    val gson = Gson()
    val json = javaClass.getClassLoader()!!.getResourceAsStream("board.json")!!.use { streamToString(it) }
    val board = gson.fromJson(json, javaClass<Board>())!!

    given("A MetaBoard") {
        val metaboard = MetaBoard(board)

        on("asking for the location of hero @1") {
            it("returns the position (2, 4)") {
                assertEquals(Position(2, 4), metaboard.hero("@1"))
            }
        }

        on("asking for the location of hero @2") {
            it("returns the position (2, 17)") {
                assertEquals(Position(2, 17), metaboard.hero("@2"))
            }
        }

        on("asking for the position of hero @3") {
            it("returns the position (19, 17)") {
                assertEquals(Position(19, 17), metaboard.hero("@3"))
            }
        }

        on("asking for the position of hero @4") {
            it("returns the position (19, 4)") {
                assertEquals(Position(19, 4), metaboard.hero("@4"))
            }
        }

        on("asking for mines owned by @1") {
            it("returns an empty list") {
                assertEquals(listOf<Position>(), metaboard.minesOwnedBy("@1"))
            }
        }

        on("asking for mines owned by @2") {
            it("returns a list with positions (10, 1) and (11, 1)") {
                assertEquals(listOf(Position(10, 1), Position(11, 1)), metaboard.minesOwnedBy("@2"))
            }
        }

        on("asking for mines owned by @3") {
            it("returns a list with a single position (8, 13)") {
                assertEquals(listOf(Position(8, 13)), metaboard.minesOwnedBy("@3"))
            }
        }

        on("asking for all neutral mines") {
            it("returns a list with a list with 9 mines") {
                val neutralMinePositions = listOf(
                        Position(2, 7),
                        Position(19, 7),
                        Position(8, 8),
                        Position(13, 8),
                        Position(13, 13),
                        Position(2, 14),
                        Position(19, 14),
                        Position(10, 20),
                        Position(11, 20)
                )

                val actualNeutralMinePositions = metaboard.neutralMines()

                neutralMinePositions.forEach {
                    assertTrue(actualNeutralMinePositions.contains(it),
                                "The value $it is not in actualMinePositions; $actualNeutralMinePositions")
                }
            }
        }

        on("asking for all taverns") {
            it("returns a list of 4 taverns") {
                val tavernPositions = listOf(
                        Position(8, 3),
                        Position(13, 3),
                        Position(8, 18),
                        Position(13, 18)
                )

                val actualTavernPositions = metaboard.taverns()

                tavernPositions.forEach {
                    assertTrue(actualTavernPositions.contains(it),
                                "The value $it is not in actualTavernPositions; $actualTavernPositions")
                }
            }
        }

        on("asking for the nearest neutral mine from @1") {
            val hero1Position = metaboard.hero("@1")

            it("returns the mine at (2, 7)") {
                val pathToNearestMine = metaboard.nearestMine(hero1Position)

                assertEquals(Position(2, 7), pathToNearestMine.destination)
            }

            it("returns a path to the mine at (2, 7)") {
                val pathToNearestMine = metaboard.nearestMine(hero1Position)

                assertEquals(Position(2, 5), pathToNearestMine.next().position)
                assertEquals(Position(2, 6), pathToNearestMine.next().position)
                assertEquals(Position(2, 7), pathToNearestMine.next().position)
            }

            it("returns all actions necessary to reach the mine at (2, 7)") {
                val pathToNearestMine = metaboard.nearestMine(hero1Position)

                assertEquals(Action.South, pathToNearestMine.next().action)
                assertEquals(Action.South, pathToNearestMine.next().action)
                assertEquals(Action.South, pathToNearestMine.next().action)
            }
        }

        on("asking for the nearest tavern from @2") {
            val hero2Position = metaboard.hero("@2")

            it("returns the tavern at (8, 18)") {
                val pathToNearestTavern = metaboard.nearestTavern(hero2Position)

                assertEquals(Position(8, 18), pathToNearestTavern.destination)
            }

            it("returns a path to the mine at (8, 18)") {
                val pathToNearestTavern = metaboard.nearestTavern(hero2Position)

                assertEquals(Position(3, 17), pathToNearestTavern.next().position)
                assertEquals(Position(3, 16), pathToNearestTavern.next().position)
                assertEquals(Position(4, 16), pathToNearestTavern.next().position)
                assertEquals(Position(5, 16), pathToNearestTavern.next().position)
                assertEquals(Position(5, 17), pathToNearestTavern.next().position)
                assertEquals(Position(5, 18), pathToNearestTavern.next().position)
                assertEquals(Position(6, 18), pathToNearestTavern.next().position)
                assertEquals(Position(7, 18), pathToNearestTavern.next().position)
                assertEquals(Position(8, 18), pathToNearestTavern.next().position)
            }

            it("returns all actiosn necessary to reach the mine at (8, 18)") {
                val pathToNearestTavern = metaboard.nearestTavern(hero2Position)

                assertEquals(Action.East, pathToNearestTavern.next().action)
                assertEquals(Action.North, pathToNearestTavern.next().action)
                assertEquals(Action.East, pathToNearestTavern.next().action)
                assertEquals(Action.East, pathToNearestTavern.next().action)
                assertEquals(Action.South, pathToNearestTavern.next().action)
                assertEquals(Action.South, pathToNearestTavern.next().action)
                assertEquals(Action.East, pathToNearestTavern.next().action)
                assertEquals(Action.East, pathToNearestTavern.next().action)
                assertEquals(Action.East, pathToNearestTavern.next().action)
            }
        }
    }
}}