import model.Node
import org.junit.jupiter.api.Test
import structures.Net
import structures.head_dependent.skipList.SkipListNet
import structures.head_dependent.splayNet.SimpleSplayListNet
import structures.head_dependent.splayNet.TreeSplayListNet
import java.util.*
import kotlin.math.pow

class RepeatedSending {
    private fun <N : Node<Int, Int, N>> netTesting(
        creation: (MutableList<Pair<Int, Int>>) -> Net<Int, Int, N>,
        degree: Int
    ) {
        val repeats = 2.0.pow(degree).toInt()
        val centers = ArrayList<Pair<Int, Int>>()
        for (i in 0..100000) {
            centers.add(Pair(i, i))
        }
        val net = creation(centers)
        val random = Random()
        var sumSteps = 0L

        for (i in 0 until 1000) {
            val start = random.nextInt(0, 100000 - 1)
            val finish = random.nextInt(start + 1, 100000)
            for (j in 0 until repeats) {
                val steps = net.send(
                    net.nodes[start],
                    finish
                ) { _, _ -> run {} }
                sumSteps += steps
            }
        }
        print("${sumSteps / 1000 / repeats}, ")
    }

    private fun <N : Node<Int, Int, N>> repeat(creation: (MutableList<Pair<Int, Int>>) -> Net<Int, Int, N>) {
        var degree = 0
        while (2.0.pow(degree) < 100000) {
            netTesting(creation, degree)
            degree++
        }
        degree = 0
        println()
        while (2.0.pow(degree) < 100000) {
            print("$degree, ")
            degree++
        }
    }

    @Test
    fun `SkipListNetTest`() {
        repeat { centers -> SkipListNet(centers) }
    }

    @Test
    fun `SimpleSplayListNetTest`() {
        repeat { centers -> SimpleSplayListNet(centers) }
    }

    @Test
    fun `TreeSplayListNetTest`() {
        repeat { centers -> TreeSplayListNet(centers) }
    }
}