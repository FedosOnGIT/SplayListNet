package structures

import model.Node

abstract class Net<K : Comparable<K>, V, N : Node<K, V, N>>(private var centers: List<Pair<K, V>>) {
    protected abstract var head: N
    protected abstract var tail: N
    val nodes: MutableList<N> = ArrayList()

    protected fun initiation() {
        for (center in centers) {
            insert(center)
        }
        var current = head.next[0]
        while (current.key != null) {
            nodes.add(current)
            current = current.next[0]
        }
    }

    abstract fun send(start: N, finish: K, function: (V, V) -> Unit): Int
    protected abstract fun insert(center: Pair<K, V>)
}