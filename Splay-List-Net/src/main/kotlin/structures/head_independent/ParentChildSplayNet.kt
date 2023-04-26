package structures.head_independent

import model.SplayNode
import structures.Net

class ParentChildSplayNet<K : Comparable<K>, V>(centers: List<Pair<K, V>>)
    : Net<K, V, SplayNode<K, V>>(centers) {
    override fun processNodes() {
        TODO("Not yet implemented")
    }

    override fun insert(center: Pair<K, V>) {
        TODO("Not yet implemented")
    }

    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        TODO("Not yet implemented")
    }
}