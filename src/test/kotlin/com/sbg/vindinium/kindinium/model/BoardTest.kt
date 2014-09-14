package com.sbg.vindinium.kindinium.model

import org.spek.Spek
import com.sbg.vindinium.kindinium.streamToString
import com.google.gson.Gson
import kotlin.test.assertEquals
import com.sbg.vindinium.kindinium.model.board.Board

class BoardTest: Spek() {{
    val gson = Gson()

    given("A JSON representation of a Board") {
        val json = javaClass.getClassLoader()!!.getResourceAsStream("board.json")!!.use { streamToString(it) }

        on("deserializing the json") {
            val board = gson.fromJson(json, javaClass<Board>())!!

            it("correctly creates a Board") {
                assertEquals(board.size, 22)
                assertEquals(board.tiles, "    ##  ############################  ##            ##      ##  $-$-  ##      ##        ##  ##    ##                    ##    ##  ##    ##  ##      []########[]      ##  ##        @1  ##  ########    ########  ##  @4                  ##            ##                                ##    ##                      $-      ##    ########    ##      $-    ##    ####    ##$-########$-##    ####    ##          ########################          ##    ################################    ####    ################################    ##          ########################          ##    ####    ##$-########$-##    ####    ##    $-      ##    ########    ##      $-                      ##    ##                                ##            ##                  @2  ##  ########    ########  ##  @3        ##  ##      []########[]      ##  ##    ##  ##    ##                    ##    ##  ##        ##      ##  $-$-  ##      ##            ##  ############################  ##    ")
            }
        }
    }
}}