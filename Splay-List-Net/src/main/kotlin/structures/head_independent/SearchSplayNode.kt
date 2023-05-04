package structures.head_independent

data class SearchSplayNode<K: Comparable<K>, V>(val middle: K,
                                                val net: LeftRightSplayNet<K, V>,
                                                val left: SearchSplayNode<K, V>,
                                                val right: SearchSplayNode<K, V>)