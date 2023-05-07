package structures.probability

import model.SplayNode
import structures.Searcher
import structures.head_dependent.splayNet.TreeSplayListNet
import java.util.Random

class ProbabilityTreeSplayListNet<K : Comparable<K>, V>(private val probability: Double, centers: List<Pair<K, V>>) :
    TreeSplayListNet<K, V>(centers) {
    var generator = Random()

    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        val chance = generator.nextDouble()
        return if (chance < probability) {
            super.send(start, finish, function)
        } else {
            Searcher.search(start, finish, function)
        }
    }
}