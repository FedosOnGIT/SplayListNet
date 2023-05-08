package structures.probability

import model.SplayNode
import structures.head_dependent.splayNet.TreeSplayListNet
import utils.Searcher
import java.util.*

class ProbabilityFrontTreeSplayListNet<K : Comparable<K>, V>(private val probability: Double,
                                                             centers: List<Pair<K, V>>) :
    TreeSplayListNet<K, V>(centers) {
    private var generator = Random()

    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        val chance = generator.nextDouble()
        return if (chance < probability) {
            super.send(start, finish, function)
        } else {
            Searcher.search(start, finish, function)
        }
    }
}