package structures.splayNet

import model.SplayNode
import structures.Net
import kotlin.math.pow

abstract class SplayListNet<K : Comparable<K>, V>(centers: List<Pair<K, V>>) : Net<K, V, SplayNode<K, V>>(centers) {
    final override var head = SplayNode<K, V>(null, null)
    final override var tail = SplayNode<K, V>(null, null)
    protected var accessCounter = 0.0
    protected var maxLevel = 0
    protected val mainStopCondition : (SplayNode<K, V>, Int) -> Boolean = { node, _ -> node == head}

    protected fun newLevel() {
        if (2.0.pow(maxLevel) <= accessCounter) {
            val level = head.topLevel

            head.hits.add(head.hits[level])
            head.next.add(tail)
            tail.previous.add(head)
            head.topLevel++
            tail.topLevel++
            maxLevel++
        }
    }

    init {
        head.next.add(tail)
        head.hits.add(0)
        tail.previous.add(head)
        initiation()
    }

    protected fun getHits(node: SplayNode<K, V>, height: Int): Int {
        if (height > node.topLevel) {
            return node.selfHits
        }
        return node.hits[height] + node.selfHits
    }

    protected fun addHits(node: SplayNode<K, V>, startHeight: Int, plus: Int) {
        for (level in startHeight..node.topLevel) {
            node.hits[level] += plus
        }
    }

    protected fun update(updated: SplayNode<K, V>,
                         changes: () -> Any,
                         plus: Int,
                         stopCondition: (SplayNode<K, V>, Int) -> Boolean) : SplayNode<K, V> {
        var current = updated
        newLevel()
        var currentHeight = 0

        while (!stopCondition(current, currentHeight)) {
            currentHeight = current.topLevel
            val previous = current.previous[currentHeight]
            addHits(previous, currentHeight + 1, plus)

            val ascendingCondition = (accessCounter) / (2.0.pow(maxLevel - currentHeight - 1))
            val descendingCondition = (accessCounter) / (2.0.pow(maxLevel - currentHeight))

            //region ascending condition
            if (previous.topLevel > currentHeight &&
                previous.hits[currentHeight + 1] - previous.hits[currentHeight] > ascendingCondition) {
                val next = previous.next[currentHeight + 1]

                current.next.add(next)
                current.previous.add(previous)
                current.topLevel++

                next.previous[currentHeight + 1] = current
                previous.next[currentHeight + 1] = current

                current.hits.add(previous.hits[currentHeight + 1] - previous.hits[currentHeight] - current.selfHits)
                previous.hits[currentHeight + 1] = previous.hits[currentHeight]
            }
            //endregion
            //region descending condition
            else if (getHits(previous, currentHeight) + getHits(current, currentHeight) <= descendingCondition) {
                val next = current.next[currentHeight]
                previous.hits[currentHeight] += getHits(current, currentHeight)

                previous.next[currentHeight] = current.next.removeAt(currentHeight)
                next.previous[currentHeight] = current.previous.removeAt(currentHeight)
                current.hits.removeAt(currentHeight)
                current.topLevel--
            }
            //endregion
            current = previous
            changes()
        }
        return current
    }

    protected fun find(start: SplayNode<K, V>, key: K, changes: () -> Any): SplayNode<K, V> {
        var current = start
        for (currentLevel in current.topLevel downTo 0) {
            var next = current.next[currentLevel]
            while (next.key != null && next.key!! < key) {
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

    override fun insert(center: Pair<K, V>) {
        val key = center.first
        val parent = find(head, key) {}
        if (parent.key == key) {
            throw RuntimeException("Key $key already exists")
        }
        val next = parent.next[0]

        val node = SplayNode(key, center.second, selfHits = 0)
        node.hits.add(0)
        node.next.add(next)
        node.previous.add(parent)
        parent.next[0] = node
        next.previous[0] = node
        update(node, {}, 1, mainStopCondition)
    }

}