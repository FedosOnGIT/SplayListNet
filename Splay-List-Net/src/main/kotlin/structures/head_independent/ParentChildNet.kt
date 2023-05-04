package structures.head_independent

import model.Node
import model.ParentChildSplayNode
import structures.Net
import structures.head_independent.model.*

open class ParentChildNet<K : Comparable<K>, V, N : Node<K, V>>(val centers: List<Pair<K, V>>,
                                                                private val interval: Int,
                                                                val leafFunction: (List<Pair<K, V>>) -> Net<K, V, N>) :
    Net<K, V, ParentChildSplayNode<K, V>>(centers) {
    private val root: SearchSplayNode<K, V, N>

    init {
        root = create(0, centers.size)
    }

    private fun create(leftIndex: Int, rightIndex: Int): SearchSplayNode<K, V, N> {
        val subCenters = centers.subList(leftIndex, rightIndex)
        if (rightIndex - leftIndex > interval) {
            return SearchSplayNode(null, null,
                                   leafFunction(subCenters),
                                   null, null)
        }
        val middleIndex = (leftIndex + rightIndex + 1) / 2
        val middle = centers[middleIndex].first
        val net = LeftRightSplayNet(middle, subCenters)
        val left = create(leftIndex, middleIndex)
        val right = create(middleIndex, rightIndex)
        for (index in 0 until rightIndex - leftIndex) {
            val node = net.nodes[index]
            if (node.key!! <= middle) {
                node.link = left[index]
            } else {
                node.link = right[index]
            }
        }
        return SearchSplayNode(middle, net, null, left, right)
    }

    override fun processNodes() {
        nodes.addAll(root.net!!.nodes)
    }

    override fun insert(center: Pair<K, V>) {}

    override fun send(start: ParentChildSplayNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        var current = root
        var begin = start as Node<K, V>
        while (!current.isLeaf() && !(begin.key!! <= current.middle!! && finish > current.middle!!)) {
            current = if (current.middle!! < begin.key!!) {
                current.right!!
            } else {
                current.left!!
            }
            begin = (begin as ParentChildSplayNode).link!!
        }
        return current.send(begin, finish, function)
    }
}