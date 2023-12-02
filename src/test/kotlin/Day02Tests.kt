import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day02Tests {

    @Test
    fun `Max cubes of each colour`() {
        val gameInput = "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        val cubeCounter = CubeCounter()
        val cubeCounts = cubeCounter.count(gameInput)
        assertThat(cubeCounts.findMaxRed()).isEqualTo(4)
        assertThat(cubeCounts.findMaxGreen()).isEqualTo(2)
        assertThat(cubeCounts.findMaxBlue()).isEqualTo(6)
    }

    @Test
    fun `Find possible games`() {
        val input = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent().lines()

        val gameResults = GameResults(input, CubeCounter())
        assertThat(gameResults.findPossibleGames(red = 12, green = 13, blue = 14)).isEqualTo(setOf(1, 2, 5))
    }

    @Test
    fun `Calculate cube power`() {
        val gameInput = "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        val cubeCounter = CubeCounter()
        val cubeCounts = cubeCounter.count(gameInput)

        assertThat(cubeCounts.calculatePower()).isEqualTo(48)
    }

    @Test
    fun `Sum cube powers`() {
        val input = """
            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent().lines()

        val gameResults = GameResults(input, CubeCounter())
        assertThat(gameResults.sumCubePowers()).isEqualTo(2286)
    }
}
