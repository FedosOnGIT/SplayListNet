package structures

import model.SplayNode
import kotlin.math.pow

class SplayUpdater<K : Comparable<K>, V>(val newLevel: (Double, Int) -> Boolean) {
    var accessCounter = 0.0
    var maxLevel = 0
    private fun getHits(node: SplayNode<K, V>, height: Int): Int {
        if (height > node.topLevel) {
            return node.selfHits
        }
        return node.hits[height] + node.selfHits
    }

    private fun addHits(node: SplayNode<K, V>, startHeight: Int, plus: Int) {
        for (level in startHeight..node.topLevel) {
            node.hits[level] += plus
        }
    }

    fun find(start: SplayNode<K, V>, key: K, changes: () -> Any,
             comparing: (K, K) -> Boolean = { one, two -> one < two }): SplayNode<K, V> {
        var current = start
        for (currentLevel in current.topLevel downTo 0) {
            var next = current.next[currentLevel]
            while (next.key != null && comparing(next.key!!, key)) {
                current = next
                next = current.next[currentLevel]
                changes()
            }

            if (next.key == key) {
                return next
            }
        }
        return current
    }

    fun insert(key: K, value: V, parent: SplayNode<K, V>, visit: (SplayNode<K, V>) -> Unit,
               stopCondition: (SplayNode<K, V>, Int) -> Boolean,
               constructor: (K, V) -> SplayNode<K, V> = { k, v -> SplayNode(k, v, selfHits = 0) }) {
        if (parent.key == key) {
            throw RuntimeException("Key $key already exists")
        }
        val next = parent.next[0]
        val node = constructor(key, value)
        node.hits.add(0)
        node.next.add(next)
        node.previous.add(parent)
        parent.next[0] = node
        next.previous[0] = node
        visit(node)
        update(node, {}, 1, stopCondition)
    }

    fun update(updated: SplayNode<K, V>, changes: () -> Any, plus: Int,
               stopCondition: (SplayNode<K, V>, Int) -> Boolean): SplayNode<K, V> {
        var current = updated
        if (newLevel(accessCounter, maxLevel)) {
            maxLevel++
        }
        var currentHeight = current.topLevel

        while (!stopCondition(current, currentHeight)) {
//            if (current.previous.size < currentHeight) {
//                break
//            }
            val previous = current.previous[currentHeight]
            addHits(previous, currentHeight + 1, plus)

            var ascendingCondition = (accessCounter) / (2.0.pow(maxLevel - currentHeight - 1))
            val descendingCondition = (accessCounter) / (2.0.pow(maxLevel - currentHeight))

            //region ascending condition
            while (previous.topLevel > currentHeight && previous.hits[currentHeight + 1] - previous.hits[currentHeight] > ascendingCondition) {
                val next = previous.next[currentHeight + 1]

                current.next.add(next)
                current.previous.add(previous)
                current.topLevel++

                next.previous[currentHeight + 1] = current
                previous.next[currentHeight + 1] = current

                current.hits.add(previous.hits[currentHeight + 1] - previous.hits[currentHeight] - current.selfHits)
                previous.hits[currentHeight + 1] = previous.hits[currentHeight]
                currentHeight = current.topLevel
                ascendingCondition = (accessCounter) / (2.0.pow(maxLevel - currentHeight - 1))
            }
            //endregion
            //region descending condition
            if (getHits(previous, currentHeight) + getHits(current, currentHeight) <= descendingCondition) {
                val next = current.next[currentHeight]
                previous.hits[currentHeight] += getHits(current, currentHeight)

                previous.next[currentHeight] = current.next.removeAt(currentHeight)
                next.previous[currentHeight] = current.previous.removeAt(currentHeight)
                current.hits.removeAt(currentHeight)
                current.topLevel--
            }
            //endregion
            current = previous
            currentHeight = current.topLevel
            changes()
        }
        return current
    }

}