package structures.head_dependent.splayNet

import model.SplayNode
import structures.SplayUpdater
import structures.head_dependent.HeadDependentNet
import kotlin.math.pow

abstract class SplayListNet<K : Comparable<K>, V>(centers: List<Pair<K, V>>) :
    HeadDependentNet<K, V, SplayNode<K, V>>(centers) {
    final override var head = SplayNode<K, V>(null, null)
    final override var tail = SplayNode<K, V>(null, null)
    protected val updater: SplayUpdater<K, V>
    protected val mainStopCondition: (SplayNode<K, V>, Int) -> Boolean = { node, _ -> node == head }

    private fun newLevel(accessCounter: Double, maxLevel: Int): Boolean {
        if (2.0.pow(maxLevel) <= accessCounter) {
            val level = head.topLevel

            head.hits.add(head.hits[level])
            head.next.add(tail)
            tail.previous.add(head)
            head.topLevel++
            tail.topLevel++
            return true
        }
        return false
    }

    init {
        updater = SplayUpdater(this::newLevel)
        head.next.add(tail)
        head.hits.add(0)
        tail.previous.add(head)
        initiation()
    }

    protected fun visit(updated: SplayNode<K, V>) {
        updated.selfHits++
        updater.accessCounter++
    }

    override fun insert(center: Pair<K, V>) {
        val key = center.first
        val parent = updater.find(head, key, {})
        updater.insert(center.first, center.second, parent, this::visit, mainStopCondition)
    }

}