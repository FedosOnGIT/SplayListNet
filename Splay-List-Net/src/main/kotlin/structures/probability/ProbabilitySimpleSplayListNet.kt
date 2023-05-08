package structures.probability

import model.SplayNode
import structures.head_dependent.splayNet.SimpleSplayListNet
import utils.ProbabilitySearcher

class ProbabilitySimpleSplayListNet<K : Comparable<K>, V>(probability: Double, centers: List<Pair<K, V>>) :
    SimpleSplayListNet<K, V>(centers) {
    private val searcher = ProbabilitySearcher<K, V>(probability)
    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        return if (searcher.decide()) {
            return super.send(start, finish, function)
        } else {
            searcher.send(start, finish, function, mainStopCondition)
        }
    }
}