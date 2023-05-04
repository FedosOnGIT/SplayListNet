package model

open class SplayNode<K : Comparable<K>, V>(
    override val key: K?,
    override var value: V?,
    var selfHits: Int = 0,
    var previous: MutableList<SplayNode<K, V>> = ArrayList(),
    var hits: MutableList<Int> = ArrayList()
) : Node<K, V, SplayNode<K, V>>(key, value)
