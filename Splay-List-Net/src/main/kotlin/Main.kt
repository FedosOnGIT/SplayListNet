import structures.head_dependent.splayNet.TreeSplayListNet
import java.util.*

fun main() {
    val centers = ArrayList<Pair<Int, Int>>()
    for (i in 0 until 10) {
        centers.add(Pair(i, i))
    }
    val net = TreeSplayListNet(centers)
    val random = Random()
    var sumSteps = 0L

    net.send(net.nodes[5], 8) { _, _ -> }

//    for (i in 0 until  10000) {
//        val start = random.nextInt(0, 1000 - 1)
//        val finish = random.nextInt(start + 1, 1000)
//        val steps = net.send(net.nodes[start], finish) {first, second -> println("Request from $first to $second succeeded") }
//        println("Number of steps = $steps")
//        sumSteps += steps
//    }
//    println("Average number of steps ${sumSteps / 10000}")
}