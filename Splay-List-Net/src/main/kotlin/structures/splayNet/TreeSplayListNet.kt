package structures.splayNet

import model.SplayNode
import structures.Net
import kotlin.math.pow

class TreeSplayListNet<K : Comparable<K>, V>(
    centers: List<Pair<K, V>>
) : SplayListNet<K, V>(centers) {

    init {

    }

    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Int {
        var steps = 0
        val changes = { steps++ }

        val stopCondition: (SplayNode<K, V>, Int) -> Boolean = { node, height ->
            node.topLevel >= height && node.next[height].key!! >= finish
        }

        val parent = update(start, changes, 1, stopCondition)
        val end = find(parent, finish, changes)
        if (end.key != finish) {
            throw RuntimeException("No such key as $finish")
        }

        update(start, changes, 1, stopCondition)
        update(parent, changes, 2, mainStopCondition)

        function(start.value!!, end.value!!)
        return steps
    }
}