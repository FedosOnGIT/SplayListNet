package model

class SkipNode<K: Comparable<K>, V>(
    override val key: K?,
    override var value: V?,
    override var next: MutableList<SkipNode<K, V>> = ArrayList(),
    override var topLevel: Int = 0,
) : ServerNode<K, V, SkipNode<K, V>>(key, value)