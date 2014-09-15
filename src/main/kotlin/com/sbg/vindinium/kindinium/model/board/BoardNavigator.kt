package com.sbg.vindinium.kindinium.model.board

import java.util.HashMap
import com.sbg.vindinium.kindinium.model.Position
import java.util.ArrayList

/**
 * The BoardNavigator knows how to navigate a MetaBoard and create the shortest
 * path to and from any Position.
 */
private class BoardNavigator(val metaboard: MetaBoard) {
    /**
     * Given a set of Positions, finds a path to the nearest one, if any.
     *
     * @return a Path to the nearest position in targets, or null if no Path could be found.
     */
    public fun pathToNearest(targets: List<Position>, start: Position): Path? {
        val pathsToStart = constructPathsFrom(start)

        val nearestTarget = findNearest(start, targets, pathsToStart)

        return if (nearestTarget != null) Path(pathsToStart, nearestTarget, start) else null
    }

    /**
     * Constructs the shortest path to a given position.
     *
     * @return a Path to the given Position, or null if no Path could be found.
     */
    public fun pathTo(to: Position, start: Position): Path? {
        return pathToNearest(listOf(to), start)
    }

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

        addIfViableNeighbor(neighbors, Position(position.x - 1, position.y))
        addIfViableNeighbor(neighbors, Position(position.x + 1, position.y))
        addIfViableNeighbor(neighbors, Position(position.x, position.y - 1))
        addIfViableNeighbor(neighbors, Position(position.x, position.y + 1))

        return neighbors
    }

    private fun addIfViableNeighbor(neighbors: ArrayList<Position>, candidate: Position) {
        if (candidate.x > 0 && candidate.x < metaboard.boardSize() &&
            candidate.y > 0 && candidate.y < metaboard.boardSize() &&
            metaboard[candidate] != BoardTile.IMPASSABLE_WOOD) {

            neighbors.add(candidate)
        }
    }

    private fun findNearest(start: Position, to: List<Position>, pathsToStart: HashMap<Position, Position?>): Position? {
        var nearest: Position? = null
        var stepsToNearest = 9000

        for (position in to) {
            val stepsToPosition = countSteps(position, pathsToStart)
            if (stepsToPosition < stepsToNearest) {
                nearest = position
                stepsToNearest = stepsToPosition
            }
        }

        return nearest
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