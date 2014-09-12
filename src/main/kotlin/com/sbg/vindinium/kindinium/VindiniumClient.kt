/*
Copyright 2014 Christian Broomfield

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.sbg.vindinium.kindinium

import java.net.URL
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType
import com.google.gson.Gson
import com.sbg.vindinium.kindinium.model.Response
import javax.ws.rs.BadRequestException
import javax.ws.rs.client.WebTarget
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import java.io.InputStreamReader
import javax.ws.rs.NotFoundException
import com.sbg.vindinium.kindinium.bot.Bot
import com.sbg.vindinium.kindinium.model.MetaBoard

/**
 * Controller class that starts, runs, and terminates a session with the Vindinium server. Accepts
 * and forwards the bot's decisions to the server.
 *
 * The VindiniumClient supports training and arena modes, and can optionally specify the maximum number of turns
 * and the map on which to spar.
 */
class VindiniumClient(val bot: Bot) {
    private val serverUrl = "http://vindinium.org/api"

    private val gson = Gson()
    private val client = ClientBuilder.newClient()!!

    /**
     * Play an official arena match.
     */
    fun playInArenaMode() {
        val url = "${serverUrl}/arena?key=${API_KEY}"

        play(url)
    }

    /**
     * Play a training game.
     * @param turnsToPlay the maximum number of turns a game should last
     * @param map the predefined map on which to play, or null for a random map
     */
    fun playInTrainingMode(turnsToPlay: Int, map: String?) {
        val url =
                if (map != null)
                    "${serverUrl}/training?key=${API_KEY}&turns=${turnsToPlay}&map=${map}"
                else
                    "${serverUrl}/training?key=${API_KEY}&turns=${turnsToPlay}"

        play(url)
    }

    private fun play(startingUrl: String) {
        var response = sendRequest(startingUrl)

        println("The game has started, you can view it here: ${response.viewUrl}")
        println("Please don't exit the program until it has notified you of game completion")

        while (!response.game.finished) {
            System.out.print("\rTurn ${response.game.turn} of ${response.game.maxTurns}")
            System.out.flush()

            val metaBoard = MetaBoard(response.game.board)
            val nextAction = bot.chooseAction(response.game, metaBoard)

            response = sendRequest("${response.playUrl}?key=${API_KEY}&dir=${nextAction.name}")
        }

        println("\nThe game has finished.")
    }

    private fun sendRequest(url: String): Response {
        val serverTarget = client.target(url)!!

        val jsonResponse = post(serverTarget)

        return gson.fromJson(jsonResponse, javaClass<Response>())!!
    }

    private fun post(serverTarget: WebTarget): String {
        try {
            return serverTarget.request(MediaType.APPLICATION_JSON)!!
                                 .post(null, javaClass<String?>())!!
        } catch (e: BadRequestException) {
            formatAndDisplayError(e.getResponse()!!)

            throw e
        } catch (e: NotFoundException) {
            formatAndDisplayError(e.getResponse()!!)

            throw e
        }
    }

    private fun formatAndDisplayError(response: javax.ws.rs.core.Response) {
        val entity = response.getEntity() as ByteArrayInputStream

        InputStreamReader(entity).use {
            println("There was an error with our request and the server said: ${it.readText()}")
        }
    }

    class object {
        /*
         * TODO: You must specify your own API key in order to play the game.
         */
        val API_KEY = "u3d1qxgf"
    }
}