import model.Node
import org.junit.Test
import structures.Net
import structures.head_dependent.skipList.SkipListNet
import structures.head_dependent.splayNet.SimpleSplayListNet
import structures.head_dependent.splayNet.TreeSplayListNet
import structures.head_independent.SkipParentChildNet
import structures.head_independent.TreeSplayParentChildNet
import structures.probability.ProbabilityFrontTreeSplayListNet
import structures.probability.ProbabilitySimpleSplayListNet
import structures.probability.ProbabilityTreeSplayListNet
import java.util.*

class RandomSending {
    private fun <N : Node<Int, Int>> netTesting(creation: (MutableList<Pair<Int, Int>>) -> Net<Int, Int, N>) {
        val centers = ArrayList<Pair<Int, Int>>()
        for (i in 0..10000) {
            centers.add(Pair(i, i))
        }
        val net = creation(centers)
        val random = Random()
        var sumSteps = 0L

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

    @Test
    fun `TreeSplayListNetTest`() {
        netTesting { centers -> TreeSplayListNet(centers) }
    }

    @Test
    fun `SkipParentChildNetTest`() {
        netTesting { centers -> SkipParentChildNet(centers, 100) }
    }

    @Test
    fun `TreeSplayParentChildNetTest`() {
        netTesting { centers -> TreeSplayParentChildNet(centers, 100) }
    }

    @Test
    fun `ProbabilitySimpleSplayListNetTest`() {
        netTesting { centers -> ProbabilitySimpleSplayListNet(0.1, centers) }
    }

    @Test
    fun `ProbabilityTreeSplayListNetTest`() {
        netTesting { centers -> ProbabilityTreeSplayListNet(0.1, centers) }
    }

    @Test
    fun `ProbabilityFrontTreeSplayListNetTest`() {
        netTesting { centers -> ProbabilityFrontTreeSplayListNet(0.1, centers) }
    }
}