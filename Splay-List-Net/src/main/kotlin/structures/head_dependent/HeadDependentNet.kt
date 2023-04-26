package structures.head_dependent

import model.Node
import structures.Net

abstract class HeadDependentNet<K : Comparable<K>, V, N : Node<K, V, N>>(centers: List<Pair<K, V>>) :
    Net<K, V, N>(centers) {
    protected abstract var head: N
    protected abstract var tail: N
    override fun processNodes() {
        var current = head.next[0]
        while (current.key != null) {
            nodes.add(current)
            current = current.next[0]
        }
    }
}