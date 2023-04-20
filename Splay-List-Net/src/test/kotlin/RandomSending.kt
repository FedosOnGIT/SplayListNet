import model.Node
import org.junit.jupiter.api.Test
import structures.Net
import structures.skipList.SkipListNet
import structures.splayNet.SimpleSplayListNet
import java.util.*

class RandomSending {
    private fun <N : Node<Int, Int, N>> netTesting(creation: (MutableList<Pair<Int, Int>>) -> Net<Int, Int, N>) {
        val centers = ArrayList<Pair<Int, Int>>()
        for (i in 0..10000) {
            centers.add(Pair(i, i))
        }
        val net = creation(centers)
        val random = Random()
        var sumSteps = 0

        for (i in 0 until 100000) {
            val start = random.nextInt(0, 10000 - 1)
            val finish = random.nextInt(start + 1, 10000)
            val steps = net.send(
                net.nodes[start],
                finish
            ) { _, _ -> run {} }
            sumSteps += steps
        }
        println("Average number of steps ${sumSteps / 100000}")
    }

    @Test
    fun `SkipListNetTest`() {
        netTesting { centers -> SkipListNet(centers) }
    }

    @Test
    fun `SimpleSplayListNetTest`() {
        netTesting { centers -> SimpleSplayListNet(centers) }
    }
}