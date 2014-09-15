package com.sbg.vindinium.kindinium

import org.spek.Spek
import kotlin.test.assertEquals

class FunctionalUtilsTest: Spek() {{
    given("the pairwise function") {
        on("on an empty list") {
            it("returns an empty list") {
                val actual = listOf<Int>().pairwise()

                assertEquals(listOf<Int>(), actual)
            }
        }

        on("a list of one element") {
            it("returns an empty list") {
                val actual = listOf(1).pairwise()

                assertEquals(listOf<Int>(), actual)
            }
        }

        on("a list of two elements") {
            it("returns a single pair with both elements") {
                val actual = listOf(1, 2).pairwise()

                assertEquals(listOf(Pair(1, 2)), actual)
            }
        }

        on("a list with multiple elements") {
            val expected = listOf(
                    Pair(1, 2),
                    Pair(2, 3),
                    Pair(3, 4),
                    Pair(4, 5)
            )

            val actual = listOf(1, 2, 3, 4, 5).pairwise()

            assertEquals(expected, actual)
        }
    }
}}