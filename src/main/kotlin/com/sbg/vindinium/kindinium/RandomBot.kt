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

import com.sbg.vindinium.kindinium.model.Game
import java.util.Random

/**
 * The RandomBot follows no particular logic when deciding which action to take.
 */
class RandomBot: Bot {
    val random = Random()

    val possibleActions = Action.values()

    override fun chooseAction(game: Game): Action {
        return possibleActions[random.nextInt()]
    }
}