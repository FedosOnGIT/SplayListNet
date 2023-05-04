package structures.head_independent

import model.SkipNode
import model.SplayNode
import structures.head_dependent.skipList.SkipListNet
import structures.head_dependent.splayNet.TreeSplayListNet

class TreeSplayParentChildNet<K: Comparable<K>, V>(centers: List<Pair<K, V>>, interval: Int):
    ParentChildNet<K, V, SplayNode<K, V>>(centers, interval, { subCenters -> TreeSplayListNet(subCenters) })