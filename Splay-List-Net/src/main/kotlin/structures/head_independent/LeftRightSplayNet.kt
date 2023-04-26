package structures.head_independent

import model.SplayNode
import structures.Net
import structures.SplayUpdater
import kotlin.math.pow

class LeftRightSplayNet<K: Comparable<K>, V>(val middle: K) {
    private var head = SplayNode<K, V>(null, null)
    private var leftMiddle = SplayNode<K, V>(null, null)
    private var rightMiddle = SplayNode<K, V>(null, null)
    private var tail = SplayNode<K, V>(null, null)
    private val stopCondition: (SplayNode<K, V>, Int) -> Boolean = {node, _ -> node.key == null}
    private val updater: SplayUpdater<K, V>

    private fun newLevel(accessCounter: Double, maxLevel: Int): Boolean {
        if (2.0.pow(maxLevel) <= accessCounter) {
            leftMiddle.hits.add(leftMiddle.hits[updater.maxLevel])
            rightMiddle.hits.add(rightMiddle.hits[updater.maxLevel])

            head.previous.add(leftMiddle)
            leftMiddle.next.add(head)

            leftMiddle.previous.add(rightMiddle)
            rightMiddle.previous.add(leftMiddle)

            rightMiddle.next.add(tail)
            tail.previous.add(rightMiddle)

            head.topLevel++
            leftMiddle.topLevel++
            rightMiddle.topLevel++
            tail.topLevel++
            return true
        }
        return false
    }

    init {
        leftMiddle.hits.add(0)
        rightMiddle.hits.add(0)

        head.previous.add(leftMiddle)
        leftMiddle.next.add(head)

        leftMiddle.previous.add(rightMiddle)
        rightMiddle.previous.add(leftMiddle)

        rightMiddle.next.add(tail)
        tail.previous.add(rightMiddle)

        updater = SplayUpdater {accessCounter, maxLevel -> newLevel(accessCounter, maxLevel)}
    }

    fun visit(node: SplayNode<K, V>) {
        node.selfHits++
        if (node.key!! <= middle) {
            updater.accessCounter++
        }
    }

    fun insert(center: Pair<K, V>) {
        val key = center.first
        val parent = if (key <= middle) {
            updater.find(leftMiddle, key) {}
        } else {
            updater.find(rightMiddle, key) {}
        }

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
        updater.update(node, {}, 1, stopCondition)
    }

    fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Int {
        TODO("Not yet implemented")
    }


}