package structures.head_dependent.skipList

import model.SkipNode
import structures.Net
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
        var steps = 0L

        var current = start
        var currentLevel = start.topLevel
        //region high
        while (current.next[currentLevel].key != null && current.next[currentLevel].key!! < finish) {
            current = current.next[currentLevel]
            currentLevel = current.topLevel
            steps++
        }
        //endregion

        //region down
        for (index in currentLevel downTo 0) {
            while (current.next[index].key != null && current.next[index].key!! <= finish) {
                current = current.next[index]
                steps++
            }

            if (current.key == finish) {
                function(start.value!!, current.value!!)
                return steps
            }
        }
        //endregion

        throw RuntimeException("Request between ${start.key} and $finish not succeeded")
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

    private fun find(node: SkipNode<K, V>): SkipNode<K, V> {
        if (node.key == null) {
            throw RuntimeException("Key must not be null")
        }
        var current = head
        for (currentLevel in head.topLevel - 1..0) {
            while (current.next[currentLevel].key != null && current.next[currentLevel].key!! < node.key) {
                current = current.next[currentLevel]
            }

            if (current.key == node.key) {
                return current
            }
        }
        return current
    }
}