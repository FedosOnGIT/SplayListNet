package utils

import model.SplayNode
import java.util.*

class ProbabilitySearcher<K : Comparable<K>, V>(private val probability: Double) {
    private var generator = Random()

    fun decide(): Boolean {
        val chance = generator.nextDouble()
        return chance < probability
    }

    fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit,
             stopCondition: (SplayNode<K, V>, Int) -> Boolean): Long {
        var steps = 0L
        val changes = { steps++ }
        val parent = Searcher.backFind(start, changes, stopCondition)
        val end = Searcher.find(parent, finish, changes)
        if (end.key != finish) {
            throw RuntimeException("No such key as $finish")
        }
        function(start.value!!, end.value!!)
        return steps
    }
}