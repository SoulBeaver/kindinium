package com.sbg.vindinium.kindinium.model.board

import java.util.HashMap
import com.sbg.vindinium.kindinium.model.Position

private class BoardNavigator(val metaboard: MetaBoard) {
    private var from: Position = Position(0, 0)
    private var pathsFrom = hashMapOf<Position, Position?>()
/*
    public fun findNearest(from: Position, to: List<Position>): Path? {
        if (this.from != from) {
            pathsFrom = constructPathsFrom(from)
            this.from = from
        }

        return findNearest(to)
    }

    private fun findNearest(to: List<Position>): Path? {
        var nearest: Position? = null
        var stepsToNearest = 9000

        for (position in to) {
            val stepsToPosition = countSteps(position, pathsFrom)
            if (stepsToPosition < stepsToNearest) {
                nearest = position
                stepsToNearest = stepsToPosition
            }
        }

        var path = arrayListOf<Position>()

        return nearest
    }
*/
    private fun constructPathsFrom(start: Position): HashMap<Position, Position?> {
        val frontier = arrayListOf(start)
        val cameFrom = hashMapOf<Position, Position?>(start to null)

        while (frontier.isNotEmpty()) {
            val current = frontier.first!!
            frontier.remove(0)

            for (next in neighbors(current)) {
                if (!cameFrom.contains(next)) {
                    frontier.add(next)
                    cameFrom[next] = current
                }
            }
        }

        return cameFrom
    }

    private fun neighbors(position: Position): List<Position> {
        val neighbors = arrayListOf<Position>()

        if (position.x - 1 >= 0)
            neighbors.add(Position(position.x - 1, position.y))
        if (position.x + 1 < metaboard.boardSize())
            neighbors.add(Position(position.x + 1, position.y))

        if (position.y - 1 >= 0)
            neighbors.add(Position(position.x, position.y - 1))
        if (position.y + 1 < metaboard.boardSize())
            neighbors.add(Position(position.x, position.y + 1))

        return neighbors
    }

    private fun countSteps(target: Position, paths: HashMap<Position, Position?>): Int {
        var count = 0
        var current: Position? = target

        while (current != null) {
            count++
            current = paths[current]
        }

        return count - 1
    }
}