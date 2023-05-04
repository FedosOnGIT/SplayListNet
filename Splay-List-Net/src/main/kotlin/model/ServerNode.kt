package model

abstract class ServerNode<K : Comparable<K>, V, N : ServerNode<K, V, N>>(
    override val key: K?,
    override var value: V?,
    open var next: MutableList<N> = ArrayList(),
    open var topLevel: Int = 0
) : Node<K, V>(key, value)
