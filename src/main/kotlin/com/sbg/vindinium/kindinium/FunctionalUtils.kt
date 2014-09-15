package com.sbg.vindinium.kindinium

/**
 * Returns a sequence of pairs composed from the previous element and the current one.
 *
 * Examples:
 *
 * - listOf<Int>().pairwise() => []
 * - listOf(1).pairwise() => []
 * - listOf(1, 2).pairwise() => [(1, 2)]
 * - listOf(1, 2, 3).pairwise() => [(1, 2), (2, 3)]
 *
 *
 */
public fun <T> Iterable<T>.pairwise(): List<Pair<T, T>> {
    if (this.count() <= 1)
        return listOf()

    return this.fold(listOf(this.first() to this.first())) {
        (list: List<Pair<T, T>>, current: T) ->
        list + Pair(list.last!!.second!!, current)
    }.drop(2)
}