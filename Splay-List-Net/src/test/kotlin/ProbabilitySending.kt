import org.junit.Test
import structures.head_dependent.splayNet.SplayListNet
import structures.probability.ProbabilityFrontTreeSplayListNet
import structures.probability.ProbabilitySimpleSplayListNet
import structures.probability.ProbabilityTreeSplayListNet

class ProbabilitySending {
    private fun probabilities(creation: (Double, MutableList<Pair<Int, Int>>) -> SplayListNet<Int, Int>) {
        val denominators = listOf(2.0, 3.0, 5.0, 10.0, 20.0, 50.0, 100.0)
        for (denominator in denominators) {
            println("1 / $denominator")
            RepeatedSending.repeat { centers -> creation(1.0 / denominator, centers) }
            println()
        }
    }

    @Test
    fun `ProbabilitySimpleSplayListNetTest`() {
        probabilities { probability, centers -> ProbabilitySimpleSplayListNet(probability, centers) }
    }

    @Test
    fun `ProbabilityTreeSplayListNetTest`() {
        probabilities { probability, centers -> ProbabilityTreeSplayListNet(probability, centers) }
    }

    @Test
    fun `ProbabilityFrontTreeSplayListNetTest`() {
        probabilities { probability, centers -> ProbabilityFrontTreeSplayListNet(probability, centers) }
    }
}