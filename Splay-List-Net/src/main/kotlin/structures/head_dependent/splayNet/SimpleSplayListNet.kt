package structures.head_dependent.splayNet

import model.SplayNode
import utils.Searcher

open class SimpleSplayListNet<K : Comparable<K>, V>(centers: List<Pair<K, V>>) : SplayListNet<K, V>(centers) {

    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        var steps = 0L
        val changes = { steps++ }

        visit(start)
        updater.update(start, changes, 1, mainStopCondition)

        val end = Searcher.find(head, finish, changes)
        if (end.key != finish) {
            throw RuntimeException("No such key as $finish")
        }
        visit(end)
        updater.update(end, changes, 1, mainStopCondition)

        function(start.value!!, end.value!!)
        return steps
    }


}