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

/**
 * Creates a sequential pairing of characters out of a string. Unliked {@see pairwise}, does not
 * create a list of (last, next), but (next, next+1).
 *
 * Examples:
 *
 * - "".paired() => []
 * - "a".paired() => []
 * - "ab".paired() => ["ab"]
 * - "abc".paired() => ["ab"]
 * - "abcd".paired() => ["ab", "cd"]
 */
public fun String.paired(): List<String> {
    if (this.count() <= 1)
        return listOf()

    val pairs = arrayListOf<String>()

    var i = 0
    while (i != this.size) {
        if (i + 1 == this.size)
            break

        pairs.add("${this[i]}${this[i + 1]}")
        i += 2
    }

    return pairs
}