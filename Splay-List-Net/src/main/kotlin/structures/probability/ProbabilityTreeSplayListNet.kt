package structures.probability

import model.SplayNode
import structures.head_dependent.splayNet.TreeSplayListNet
import utils.ProbabilitySearcher

class ProbabilityTreeSplayListNet<K : Comparable<K>, V>(probability: Double, centers: List<Pair<K, V>>) :
    TreeSplayListNet<K, V>(centers) {
    private val searcher = ProbabilitySearcher<K, V>(probability)

    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        return if (searcher.decide()) {
            super.send(start, finish, function)
        } else {
            val stopCondition: (SplayNode<K, V>, Int) -> Boolean =
                { node, height -> parentStopCondition(finish, node, height) }
            searcher.send(start, finish, function, stopCondition)
        }
    }
}