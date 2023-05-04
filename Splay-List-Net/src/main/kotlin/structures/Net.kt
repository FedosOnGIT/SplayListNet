package structures

import model.Node
import model.ServerNode

abstract class Net<K : Comparable<K>, V, N : Node<K, V>>(private var centers: List<Pair<K, V>>) {

    val nodes: MutableList<N> = ArrayList()

    protected fun initiation() {
        for (center in centers) {
            insert(center)
        }
        processNodes()
    }
    protected abstract fun processNodes()
    abstract fun send(start: N, finish: K, function: (V, V) -> Unit): Long
    protected abstract fun insert(center: Pair<K, V>)
}