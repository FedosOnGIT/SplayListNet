package structures

import model.ServerNode
import model.SplayNode

class Searcher {
    companion object {
        fun <K : Comparable<K>, V, N : ServerNode<K, V, N>> search(start: ServerNode<K, V, N>, finish: K,
                                                                   function: (V, V) -> Unit): Long {
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

        fun <K : Comparable<K>, V> backSearch(start: SplayNode<K, V>,
                                              stopCondition: (SplayNode<K, V>, Int) -> Boolean): Pair<SplayNode<K, V>, Long> {
            var steps = 0L
            var current = start
            var currentLevel = start.topLevel
            while (!stopCondition(current, currentLevel)) {
                current = current.previous[currentLevel]
                currentLevel = current.topLevel
                steps++
            }
            return Pair(current, steps)
        }
    }
}