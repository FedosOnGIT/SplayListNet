import model.Node
import org.junit.jupiter.api.Test
import structures.Net
import structures.head_dependent.skipList.SkipListNet
import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min

class RealTraceTest {

    private fun <N : Node<Int, Int>> netTesting(creation: (MutableList<Pair<Int, Int>>) -> Net<Int, Int, N>, file: File): Long {
        val lines = file.readLines()
        val numbers = lines.map { it.split(" ").map { number -> number.toInt() } }
        val nodes = numbers[0][0]
        val requests = numbers[0][1]

        val centers = ArrayList<Pair<Int, Int>>()
        for (node in 0 until nodes) {
            centers.add(Pair(node, node))
        }
        val net = creation(centers)

        var total = 0L
        for (i in 1..requests) {
            val start = min(numbers[i][0], numbers[i][1])
            val finish = max(numbers[i][0], numbers[i][1])
            val steps = net.send(net.nodes[start], finish) { _, _ -> run {} }
            total += steps
        }

        return total / requests
    }

    private fun <N : Node<Int, Int>> runOnTraces(name: String, creation: (MutableList<Pair<Int, Int>>) -> Net<Int, Int, N>) {
        val readFolder = File("src/test/resources/traces")
        for (trace in readFolder.listFiles()!!) {
            File("src/test/resources/traces_results/${trace.name}")
                .writeText("$name ${netTesting(creation, trace)} \n")
        }
    }

    @Test
    fun `SkipListNetTest`() {
        runOnTraces("SkipListNet") { centers -> SkipListNet(centers) }
    }
}