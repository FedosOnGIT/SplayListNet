package model

class ParentChildSplayNode<K : Comparable<K>, V>(override val key: K?,
                                                 override var value: V?,
                                                 var link: Node<K, V>?) : SplayNode<K, V>(key, value)