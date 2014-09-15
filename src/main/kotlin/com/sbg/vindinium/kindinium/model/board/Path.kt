package com.sbg.vindinium.kindinium.model.board

import com.sbg.vindinium.kindinium.model.Position
import java.util.HashMap
import java.util.concurrent.ArrayBlockingQueue
import sun.reflect.generics.tree.Tree
import java.util.Queue
import com.sbg.vindinium.kindinium.bot.Action
import java.util.ArrayList
import com.sbg.vindinium.kindinium.pairwise

/**
 * Represents the path from start to destination as either the Positions or Actions necessary
 * to move from start to destination.
 */
data class Path(paths: Map<Position, Position?>, val destination: Position, val start: Position) {
    private val path: Queue<Position>
    private val directions: Queue<Action>

    /**
     * Get the next Step in the path. A Step represents the next Position and the
     * action required to reach it.
     *
     * @throws NoSuchElementException if there are no more Steps in the Path.
     */
    public fun next(): Step {
        return Step(path.remove(), directions.remove())
    }

    public fun isEmpty(): Boolean {
        return path.isEmpty()
    }

    public fun isNotEmpty(): Boolean {
        return path.isNotEmpty()
    }

    {
        val reversedPath = arrayListOf<Position>()

        var current: Position? = destination
        while (current != null) {
            reversedPath.add(current!!)
            current = paths[current]
        }

        path = reversedPath.reverse().toLinkedList()

        directions = path.pairwise().map { pair ->
            val (prev, curr) = pair
            identifyActionToReachPosition(prev, curr)
        }.toLinkedList()

        path.remove()
    }

    private fun identifyActionToReachPosition(from: Position, to: Position): Action {
        if (Position(from.x + 1, from.y) == to)
            return Action.East
        if (Position(from.x - 1, from.y) == to)
            return Action.West

        if (Position(from.x, from.y + 1) == to)
            return Action.South
        if (Position(from.x, from.y - 1) == to)
            return Action.North

        throw IllegalArgumentException("Unable to reach destination within one turn: $from -> $to")
    }

    public data class Step(val position: Position, val action: Action)
}
