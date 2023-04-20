package structures.splayNet

import model.SplayNode
import structures.Net
import kotlin.math.pow

class TreeSplayListNet<K : Comparable<K>, V>(
    centers: List<Pair<K, V>>
) : SplayListNet<K, V>(centers) {

    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Int {
        TODO("Not yet implemented")
    }

    override fun insert(center: Pair<K, V>) {
        TODO("Not yet implemented")
    }
}