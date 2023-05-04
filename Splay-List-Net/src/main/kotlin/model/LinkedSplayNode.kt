package model

class LinkedSplayNode<K : Comparable<K>, V, N: Node<K, V, N>>(override val key: K?,
                                                              override var value: V?,
                                                              val link: Node<K, V, N>) : SplayNode<K, V>(key, value)