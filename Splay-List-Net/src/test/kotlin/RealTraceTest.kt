import model.Node
import org.junit.FixMethodOrder
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.runners.MethodSorters
import structures.Net
import structures.head_dependent.skipList.SkipListNet
import structures.head_dependent.splayNet.SimpleSplayListNet
import structures.head_dependent.splayNet.TreeSplayListNet
import structures.head_independent.SkipParentChildNet
import structures.head_independent.TreeSplayParentChildNet
import structures.probability.ProbabilityFrontTreeSplayListNet
import structures.probability.ProbabilitySimpleSplayListNet
import structures.probability.ProbabilityTreeSplayListNet
import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.roundToInt

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RealTraceTest {

    private fun <N : Node<Int, Int>> netTesting(creation: (MutableList<Pair<Int, Int>>) -> Net<Int, Int, N>,
                                                file: File): Double {
        val lines = file.readLines()
        val numbers = lines.map { it.split(" ").map { number -> number.toInt() } }
        val nodes = numbers[0][0]
        val requests = numbers[0][1]

        val centers = ArrayList<Pair<Int, Int>>()
        for (node in 0 until nodes) {
            centers.add(Pair(node, node))
        }
        val net = creation(centers)

        var total = 0.0
        for (i in 1..requests) {
            val start = min(numbers[i][0], numbers[i][1])
            val finish = max(numbers[i][0], numbers[i][1])
            val steps = net.send(net.nodes[start], finish) { _, _ -> run {} }
            total += steps
        }

        return total / requests
    }

    private fun <N : Node<Int, Int>> runOnTraces(name: String,
                                                 creation: (MutableList<Pair<Int, Int>>) -> Net<Int, Int, N>) {
        val readFolder = File("src/test/resources/traces")
        for (trace in readFolder.listFiles()!!) {
            var steps = netTesting(creation, trace)
            steps = (steps * 100).roundToInt() / 100.0
            File("src/test/resources/traces_results/${trace.name}").appendText("$name $steps \n")
        }
    }

    @Test
    @Order(1)
    fun `1 SkipListNetTest`() {
        runOnTraces("SkipListNet") { centers -> SkipListNet(centers) }
    }

    @Test
    @Order(2)
    fun `2 SimpleSplayListNetTest`() {
        runOnTraces("SimpleSplayListNet") { centers -> SimpleSplayListNet(centers) }
    }

    @Test
    @Order(3)
    fun `3 TreeSplayListNetTest`() {
        runOnTraces("TreeSplayListNet") { centers -> TreeSplayListNet(centers) }
    }

    @Test
    @Order(4)
    fun `4 SkipParentChildNetTest`() {
        runOnTraces("SkipParentChildNet") { centers -> SkipParentChildNet(centers, 16) }
    }

    @Test
    @Order(5)
    fun `5 TreeSplayParentChildNetTest`() {
        runOnTraces("TreeSplayParentChildNet") { centers -> TreeSplayParentChildNet(centers, 16) }
    }

    @Test
    @Order(6)
    fun `6 ProbabilitySimpleSplayListNetTest`() {
        runOnTraces("ProbabilitySimpleSplayListNet") { centers ->
            ProbabilitySimpleSplayListNet(1.0 / 100.0, centers)
        }
    }

    @Test
    @Order(7)
    fun `7 ProbabilityTreeSplayListNetTest`() {
        runOnTraces("ProbabilityTreeSplayListNet") { centers ->
            ProbabilityTreeSplayListNet(1.0 / 100.0, centers)
        }
    }

    @Test
    @Order(8)
    fun `8 ProbabilityFrontTreeSplayListNetTest`() {
        runOnTraces("ProbabilityFrontTreeSplayListNet") { centers ->
            ProbabilityFrontTreeSplayListNet(1.0 / 100.0, centers)
        }
    }
}