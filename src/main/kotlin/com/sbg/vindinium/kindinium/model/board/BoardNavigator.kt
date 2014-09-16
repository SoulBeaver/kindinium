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
        val pathsFromTargets = targets.map { target -> constructPathsFrom(start, target) }

        var paths = HashMap<Position, Position?>()
        var nearest = Position(0, 0)
        var stepsToNearest = 9000
        for ((i, pathsFromTarget) in pathsFromTargets.withIndices()) {
            val stepsToTarget = countSteps(targets.get(i), pathsFromTarget)

            if (stepsToTarget < stepsToNearest && stepsToTarget != 0) {
                paths = pathsFromTarget
                nearest = targets.get(i)
                stepsToNearest = stepsToTarget
            }
        }

        return Path(paths, nearest, start)
    }

    /**
     * Constructs the shortest path to a given position.
     *
     * @return a Path to the given Position, or null if no Path could be found.
     */
    public fun pathTo(to: Position, start: Position): Path {
        val pathsToStart = constructPathsFrom(start, to)

        return Path(pathsToStart, to, start)
    }

    private fun constructPathsFrom(start: Position, to: Position): HashMap<Position, Position?> {
        val frontier = arrayListOf(start)
        val cameFrom = hashMapOf<Position, Position?>(start to null)

        while (frontier.isNotEmpty()) {
            val current = frontier.first!!
            frontier.remove(0)

            for (next in neighbors(current, to)) {
                if (!cameFrom.contains(next)) {
                    frontier.add(next)
                    cameFrom[next] = current
                }
            }
        }

        return cameFrom
    }

    private fun neighbors(position: Position, to: Position): List<Position> {
        val neighbors = arrayListOf<Position>()

        addIfViableNeighbor(neighbors, Position(position.x - 1, position.y), to)
        addIfViableNeighbor(neighbors, Position(position.x + 1, position.y), to)
        addIfViableNeighbor(neighbors, Position(position.x, position.y - 1), to)
        addIfViableNeighbor(neighbors, Position(position.x, position.y + 1), to)

        return neighbors
    }

    private fun addIfViableNeighbor(neighbors: ArrayList<Position>, candidate: Position, include: Position) {
        if (candidate.x >= 0 && candidate.x < metaboard.board.size &&
                candidate.y >= 0 && candidate.y < metaboard.board.size &&
                (metaboard[candidate] == BoardTile.ROAD || candidate == include)) {

            neighbors.add(candidate)
        }
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