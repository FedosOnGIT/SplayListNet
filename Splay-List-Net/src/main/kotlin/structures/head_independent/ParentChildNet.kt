package structures.head_independent

import model.SplayNode
import structures.Net

class ParentChildNet<K : Comparable<K>, V>(val centers: List<Pair<K, V>>, val interval: Int) : Net<K, V, SplayNode<K, V>>(centers) {
    val root: SearchSplayNode<K, V>
    init {

    }

    fun create(left: Int, right: Int) : SearchSplayNode<K, V> {
        if (right - left > )
    }
    override fun processNodes() {
        TODO("Not yet implemented")
    }

    override fun insert(center: Pair<K, V>) {

    }

    override fun send(start: SplayNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        TODO("Not yet implemented")
    }
}