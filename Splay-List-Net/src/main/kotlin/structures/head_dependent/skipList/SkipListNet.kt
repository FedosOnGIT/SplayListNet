package structures.head_dependent.skipList

import model.SkipNode
import structures.Searcher
import structures.head_dependent.HeadDependentNet
import java.util.*

class SkipListNet<K : Comparable<K>, V>(centers: List<Pair<K, V>>, val random: Random = Random()) :
    HeadDependentNet<K, V, SkipNode<K, V>>(centers) {

    override var tail = SkipNode<K, V>(null, null)
    override var head = SkipNode<K, V>(null, null)

    init {
        head.next.add(tail)
        initiation()
    }

    override fun send(start: SkipNode<K, V>, finish: K, function: (V, V) -> Unit): Long {
        return Searcher.search(start, finish, function)
    }

    override fun insert(center: Pair<K, V>) {
        var topLevel = 0
        var continueRise = random.nextBoolean()
        while (continueRise) {
            topLevel++
            continueRise = random.nextBoolean()
        }
        val node = SkipNode(center.first, center.second, Array(topLevel + 1) { tail }.toMutableList(), topLevel)
        while (head.topLevel <= topLevel) {
            head.next.add(tail)
            head.topLevel++
        }

        var current = head
        for (currentLevel in head.topLevel downTo 0) {
            while (current.next[currentLevel].key != null && current.next[currentLevel].key!! < node.key!!) {
                current = current.next[currentLevel]
            }

            if (current.key == node.key) {
                throw RuntimeException("Key ${node.key} already exists")
            }

            if (node.topLevel >= currentLevel) {
                node.next[currentLevel] = current.next[currentLevel]
                current.next[currentLevel] = node
            }
        }
    }
}