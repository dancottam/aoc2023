import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day08Tests {

    @Test
    fun `Directions can loop indefinitely`() {
        val instructions = Directions("RL")

        assertThat(instructions.next()).isEqualTo('R')
        assertThat(instructions.next()).isEqualTo('L')
        assertThat(instructions.next()).isEqualTo('R')
        assertThat(instructions.next()).isEqualTo('L')
        assertThat(instructions.next()).isEqualTo('R')
        assertThat(instructions.next()).isEqualTo('L')
    }

    @Test
    fun `Parse desert map`() {
        val input = """
            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent().lines()

        val desertMap = DesertMap.from(input)

        assertThat(desertMap.nodes).isEqualTo(mapOf(
            "AAA" to Pair("BBB", "CCC"),
            "BBB" to Pair("DDD", "EEE"),
            "CCC" to Pair("ZZZ", "GGG"),
            "DDD" to Pair("DDD", "DDD"),
            "EEE" to Pair("EEE", "EEE"),
            "GGG" to Pair("GGG", "GGG"),
            "ZZZ" to Pair("ZZZ", "ZZZ"),
        ))
    }

    @Test
    fun `Counts steps from node to node`() {
        val input = """
            LLR
            
            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent().lines()

        val steps = countStepsToNavigate("AAA", "ZZZ", input)

        assertThat(steps).isEqualTo(6)
    }

    @Test
    fun `Count steps from all nodes to reach nodes ending with`() {
        val input = """
            LR
            
            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent().lines()

        val steps = countStepsForAllNodesToReachNodesEndingWith('A', 'Z', input)

        assertThat(steps).isEqualTo(6)
    }
}
