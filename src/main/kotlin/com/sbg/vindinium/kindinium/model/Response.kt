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
package com.sbg.vindinium.kindinium.model

data class Response {
    /**
     * The token allows the player to send requests about the heroe's movement to the server.
     */
    val token = ""

    /**
     * The url to view the game in the browser.
     */
    val viewUrl = ""

    /**
     * The url with which to control the hero.
     */
    val playUrl = ""

    /**
     * The state of the game and all of its components.
     */
    val game = Game()

    /**
     * The hero under the player's control.
     */
    val hero = Hero()
}

public fun Response.getEnemies(): Array<String> {
    return Array(3, { i ->
        val id = i + 1
        if (id >= this.hero.id) {
            "@${id + 1}"
        } else {
            "@${id}"
        }
    })
}

