package model

abstract class Node<K : Comparable<K>, V, N : Node<K, V, N>>(
    open val key: K?,
    open var value: V?,
    open var next: MutableList<N> = ArrayList(),
    open var topLevel: Int = 0
)
