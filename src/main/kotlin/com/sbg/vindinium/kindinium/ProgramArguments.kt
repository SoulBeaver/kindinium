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

import com.beust.jcommander.Parameter
import com.beust.jcommander.IParameterValidator
import com.beust.jcommander.ParameterException

/**
 * Describes the possible command-line arguments allowed for kindinium.
 *
 * Currently, only two arguments are supported:
 *
 * <ul>
 *   <li>-mode (default 'training') the game mode with which to play the game</li>
 *   <li>-turns (default 300) how many turns a game should last</li>
 * </ul>
 */
class ProgramArguments {
    Parameter(names = array("-mode", "-m"),
                description = "Vinidium's supported game modes are [training, arena]. Default is training.",
                validateWith = javaClass<GameModeValidator>())
    val gameMode: String = "arena"

    Parameter(names = array("-turns", "-t"),
                description = "How long a game shoudl run. Default is 300 turns.",
                validateWith = javaClass<GameTurnsValidator>())
    val gameTurns: Int = 300

    Parameter(names = array("-map"),
                description = "Play on a predefined map from m1..m6. Default is none (randomly generated map).",
                validateWith = javaClass<GameMapValidator>())
    val gameMap: String? = null
}

private class GameModeValidator: IParameterValidator {
    private val validGameModes = array("arena", "training")

    override fun validate(name: String?, value: String?) {
        if (validGameModes.firstOrNull { it.equalsIgnoreCase(value!!) } == null)
            throw ParameterException("The only accepted game modes are 'training' and 'master'")
    }
}

private class GameTurnsValidator: IParameterValidator {
    override fun validate(name: String?, value: String?) {
        if (value!!.toInt() <= 0)
            throw ParameterException("Only positive non-null integer values are allowed.")
    }
}

private class GameMapValidator: IParameterValidator {
    private val validMaps = array("m1", "m2", "m3", "m4", "m5", "m6")

    override fun validate(name: String?, value: String?) {
        if (validMaps.firstOrNull { it.equalsIgnoreCase(value!!) } == null)
            throw ParameterException("The only accepted map values are m1..m6.")
    }
}