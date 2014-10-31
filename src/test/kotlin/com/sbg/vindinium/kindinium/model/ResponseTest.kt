package com.sbg.vindinium.kindinium.model

import com.google.gson.Gson
import com.sbg.vindinium.kindinium.streamToString
import kotlin.test.assertEquals
import org.jetbrains.spek.api.Spek

class ResponseTest : Spek() {{
    val gson = Gson()

    given("A JSON representation of a Response") {
        val json = javaClass.getClassLoader()!!.getResourceAsStream("server_response.json")!!.use { streamToString(it) }

        on("deserializing the json") {
            val response = gson.fromJson(json, javaClass<Response>())!!

            it("correctly creates a Response") {
                assertEquals(response.token, "78n0")
                assertEquals(response.viewUrl, "http://vindinium.org/z4okiary")
                assertEquals(response.playUrl, "http://vindinium.org/api/z4okiary/78n0/play")
            }
        }
    }
}
}