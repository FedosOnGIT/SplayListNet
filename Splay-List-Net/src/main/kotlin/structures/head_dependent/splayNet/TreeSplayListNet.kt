package structures.head_dependent.splayNet

import model.SplayNode

open class TreeSplayListNet<K : Comparable<K>, V>(centers: List<Pair<K, V>>) : SplayListNet<K, V>(centers) {

    init {
        for (node in nodes) {
            visit(node)
            updater.update(
                updated = node,
                changes = {},
                plus = 1,
                stopCondition = mainStopCondition
            )
        }
    }

    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        var steps = 0L
        val changes = { steps++ }

        val stopCondition: (SplayNode<K, V>, Int) -> Boolean = { node, height ->
            node.next[height].key == null || node.next[height].key!! >= finish
        }

        visit(start)
        val parent = updater.update(start, changes, 1, stopCondition)
        val end = updater.find(parent, finish, changes)
        if (end.key != finish) {
            throw RuntimeException("No such key as $finish")
        }

        visit(end)
        updater.update(end, changes, 1, stopCondition)
        updater.update(parent, changes, 2, mainStopCondition)

        function(start.value!!, end.value!!)
        return steps
    }
}