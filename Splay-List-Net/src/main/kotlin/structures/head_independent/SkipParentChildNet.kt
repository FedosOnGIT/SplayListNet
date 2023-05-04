package structures.head_independent

import model.SkipNode
import structures.head_dependent.skipList.SkipListNet

class SkipParentChildNet<K : Comparable<K>, V>(centers: List<Pair<K, V>>, interval: Int) :
    ParentChildNet<K, V, SkipNode<K, V>>(centers, interval, { subCenters -> SkipListNet(subCenters) })