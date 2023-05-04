package model

abstract class Node<K : Comparable<K>, V>(
    open val key: K?,
    open var value: V?
)