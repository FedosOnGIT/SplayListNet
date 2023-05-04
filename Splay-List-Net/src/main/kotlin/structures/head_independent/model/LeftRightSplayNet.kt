package structures.head_independent.model

import model.ParentChildSplayNode
import model.SplayNode
import structures.Net
import structures.SplayUpdater
import kotlin.math.pow

class LeftRightSplayNet<K : Comparable<K>, V>(val middle: K, centers: List<Pair<K, V>>) :
    Net<K, V, ParentChildSplayNode<K, V>>(centers) {
    private var head = ParentChildSplayNode<K, V>(null, null, null)
    private var leftMiddle = ParentChildSplayNode<K, V>(null, null, null)
    private var rightMiddle = ParentChildSplayNode<K, V>(null, null, null)
    private var tail = ParentChildSplayNode<K, V>(null, null, null)
    private val stopCondition: (SplayNode<K, V>, Int) -> Boolean = { node, _ -> node.key == null }
    private val updater: SplayUpdater<K, V>

    override fun processNodes() {
        var current = head.next[0] as ParentChildSplayNode
        while (current != tail) {
            if (current.key != null) {
                nodes.add(current)
                current = current.next[0] as ParentChildSplayNode
            }
        }
    }

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

        updater = SplayUpdater { accessCounter, maxLevel -> newLevel(accessCounter, maxLevel) }
    }

    private fun visit(node: SplayNode<K, V>) {
        node.selfHits++
        if (node.key!! <= middle) {
            updater.accessCounter++
        }
    }

    override fun insert(center: Pair<K, V>) {
        val key = center.first
        val parent = if (key <= middle) {
            updater.find(leftMiddle, key) {}
        } else {
            updater.find(rightMiddle, key) {}
        }
        updater.insert(key, center.second, parent, this::visit, stopCondition) { k, v ->
            ParentChildSplayNode(k, v, null)
        }
    }

    override fun send(start: ParentChildSplayNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        var steps = 0L
        val changes = { steps++ }

        visit(start)
        updater.update(start, changes, 1, stopCondition)
        changes()
        val end = updater.find(rightMiddle, finish, changes)
        updater.update(end, changes, 1, stopCondition)

        function(start.value!!, end.value!!)
        return steps
    }
}
