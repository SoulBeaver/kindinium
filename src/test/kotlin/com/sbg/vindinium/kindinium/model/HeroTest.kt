package com.sbg.vindinium.kindinium.model

import org.spek.Spek
import com.google.gson.Gson
import kotlin.test.assertEquals
import com.sbg.vindinium.kindinium.streamToString

class HeroTest: Spek() {{
    val gson = Gson()

    given("A JSON representation of a Hero") {
        val json = javaClass.getClassLoader()!!.getResourceAsStream("hero.json")!!.use { streamToString(it) }

        on("deserializing the json") {
            val hero = gson.fromJson(json, javaClass<Hero>())!!

            it("correctly creates a Hero") {
                assertEquals(hero.id, 1)
                assertEquals(hero.name, "IAE")
                assertEquals(hero.userId, "pwszirn4")
                assertEquals(hero.elo, 1200)
                assertEquals(hero.position(), Position(2, 4))
                assertEquals(hero.life, 100)
                assertEquals(hero.gold, 0)
                assertEquals(hero.mineCount, 0)
                assertEquals(hero.spawnPosition(), Position(2, 4))
                assertEquals(hero.crashed, false)
            }
        }
    }
}}