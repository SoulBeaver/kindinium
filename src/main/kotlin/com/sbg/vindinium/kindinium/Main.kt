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

import com.beust.jcommander.JCommander
import com.sbg.vindinium.kindinium.bot.RandomBot

fun main(args: Array<String>) {
    val programArguments = parseProgramArguments(args)

    // TODO: Choose your own bot here.
    val client = VindiniumClient(RandomBot())

    if (programArguments.gameMode == "training")
        client.playInTrainingMode(programArguments.gameTurns, programArguments.gameMap)
    else
        client.playInArenaMode()
}

private fun parseProgramArguments(args: Array<String>): ProgramArguments {
    val arguments = args.map { it: String? }.copyToArray()

    val programArguments = ProgramArguments()
    JCommander(programArguments, *arguments)

    return programArguments
}