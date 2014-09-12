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
            it("returns the position (0, 0)") {
                // TODO: Implement me.
            }
        }
    }
}}