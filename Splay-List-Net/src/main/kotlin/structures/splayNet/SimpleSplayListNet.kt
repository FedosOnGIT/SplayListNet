package structures.splayNet

import model.SplayNode
import structures.Net
import kotlin.math.pow

class SimpleSplayListNet<K : Comparable<K>, V>(centers: List<Pair<K, V>>) :
    SplayListNet<K, V>(centers) {

    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Int {
        var steps = 0
        val changes = { steps++ }

        update(start, changes, 1, mainStopCondition)

        val end = find(head, finish, changes)
        if (end.key != finish) {
            throw RuntimeException("No such key as $finish")
        }
        update(start, changes, 1, mainStopCondition)

        function(start.value!!, end.value!!)
        return steps
    }


}