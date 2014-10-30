package com.sbg.vindinium.kindinium.model

import com.google.gson.Gson
import com.sbg.vindinium.kindinium.streamToString
import kotlin.test.assertEquals
import org.jetbrains.spek.api.Spek

class GameTest: Spek() {{
    val gson = Gson()

    given("A JSON representation of a Game") {
        val json = javaClass.getClassLoader()!!.getResourceAsStream("game.json")!!.use { streamToString(it) }

        on("deserializing the json") {
            val game = gson.fromJson(json, javaClass<Game>())!!

            it("correctly creates a Game") {
                assertEquals(game.id, "z4okiary")
                assertEquals(game.turn, 0)
                assertEquals(game.maxTurns, 1200)
                assertEquals(game.heroes.size, 4)
                assertEquals(game.board.size, 22)
                assertEquals(game.finished, false)
            }
        }
    }
}}