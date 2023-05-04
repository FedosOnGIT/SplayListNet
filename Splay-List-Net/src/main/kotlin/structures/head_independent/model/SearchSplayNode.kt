package structures.head_independent.model

import model.Node
import model.ParentChildSplayNode
import structures.Net

data class SearchSplayNode<K : Comparable<K>, V, N : Node<K, V>>(val middle: K?,
                                                                 val net: LeftRightSplayNet<K, V>?,
                                                                 val leaf: Net<K, V, N>?,
                                                                 val left: SearchSplayNode<K, V, N>?,
                                                                 val right: SearchSplayNode<K, V, N>?) {
    fun isLeaf(): Boolean {
        return middle == null
    }

    fun send(start: Node<K, V>, finish: K, function: (V, V) -> Unit): Long {
        return if (isLeaf()) {
            leaf!!.send(start as N, finish, function)
        } else {
            net!!.send(start as ParentChildSplayNode<K, V>, finish, function)
        }
    }

}

operator fun <K : Comparable<K>, V, N : Node<K, V>> SearchSplayNode<K, V, N>.get(index: Int) : Node<K, V> {
    return if (isLeaf()) {
        leaf!!.nodes[index]
    } else {
        net!!.nodes[index]
    }
}