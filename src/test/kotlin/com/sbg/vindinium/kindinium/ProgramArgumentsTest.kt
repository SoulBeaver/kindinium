package com.sbg.vindinium.kindinium

import com.beust.jcommander.JCommander
import kotlin.test.assertEquals
import kotlin.test.failsWith
import com.beust.jcommander.ParameterException
import kotlin.test.assertNull
import org.jetbrains.spek.api.Spek

class ProgramArgumentsTest: Spek() {{
    given("ProgramArguments for parsing program arguments") {
        val programArguments = ProgramArguments()

        on("no custom arguments used") {
            val args = array<String?>()

            it("should return the default values 'training' and 300") {
                JCommander(programArguments, *args)

                assertEquals(programArguments.gameMode, "training")
                assertEquals(programArguments.gameTurns, 300)
                assertNull(programArguments.gameMap)
            }
        }

        on("-mode=arena") {
            val args = array<String?>("-mode", "arena")

            it("should register 'arena' as the game mode") {
                JCommander(programArguments, *args)

                assertEquals(programArguments.gameMode, "arena")
            }
        }

        on("-turns=1200") {
            val args = array<String?>("-turns", "1200")

            it("should specify 1200 turns") {
                JCommander(programArguments, *args)

                assertEquals(programArguments.gameTurns, 1200)
            }
        }

        on("-map=m1") {
            val args = array<String?>("-map", "m1")

            it("should specify map m1") {
                JCommander(programArguments, *args)

                assertEquals(programArguments.gameMap, "m1")
            }
        }

        on("a game mode other than training or arena") {
            val args = array<String?>("-mode", "duel")

            it("should error with the message that only arena and training are allowed") {
                failsWith(javaClass<ParameterException>()) {
                    JCommander(programArguments, *args)
                }
            }
        }

        on("zero or less turns") {
            val args = array<String?>("-turns", "0")

            it("should error with the message that only positive integers are allowed") {
                failsWith(javaClass<ParameterException>()) {
                    JCommander(programArguments, *args)
                }
            }
        }

        on("a map not in range m1..m6") {
            val args = array<String?>("-map", "m0")

            it("should error with the message that only map values m1..m6 are allowed") {
                failsWith(javaClass<ParameterException>()) {
                    JCommander(programArguments, *args)
                }
            }
        }
    }
}}