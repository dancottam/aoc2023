fun main() {
    fun part1(input: List<String>): Int {
        val gameResults = GameResults(input, CubeCounter())
        return gameResults.findPossibleGames(red = 12, green = 13, blue = 14).sum()
    }

    fun part2(input: List<String>): Int {
        val gameResults = GameResults(input, CubeCounter())
        return gameResults.sumCubePowers()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

class GameResults(input: List<String>, private val cubeCounter: CubeCounter) {

    private val results = parseResults(input)

    private fun parseResults(input: List<String>): Map<Int, CubeCounts> {
        val results = mutableMapOf<Int, CubeCounts>()
        for (game in input) {
            val gameId = game.substringBefore(":").substringAfter("Game ").toInt()
            val cubeCounts = cubeCounter.count(game.substringAfter(":"))

            results[gameId] = cubeCounts
        }
        return results.toMap()
    }

    fun findPossibleGames(red: Int = 0, green: Int = 0, blue: Int = 0): Set<Int> {
        return results.filter {(_, counts) ->
            counts.findMaxRed() <= red && counts.findMaxGreen() <= green && counts.findMaxBlue() <= blue
        }.keys
    }

    fun sumCubePowers(): Int {
        return results.values.sumOf { it.calculatePower() }
    }

}

data class CubeCounts(
    val red: List<Int>,
    val green: List<Int>,
    val blue: List<Int>,
) {
    fun findMaxRed(): Int {
        return red.max()
    }
    fun findMaxGreen(): Int {
        return green.max()
    }

    fun findMaxBlue(): Int {
        return blue.max()
    }

    fun calculatePower(): Int {
        return findMaxRed() * findMaxGreen() * findMaxBlue()
    }
}

class CubeCounter {

    fun count(gameInput: String): CubeCounts {

        val redCounts = mutableListOf<Int>()
        val greenCounts = mutableListOf<Int>()
        val blueCounts = mutableListOf<Int>()

        val games = gameInput.split(";")

        for (game in games) {
            val colourCounts = game.trim().split(", ")
            for (colourCount in colourCounts) {
                val count = colourCount.split(" ").first().toInt()
                val colour = colourCount.split(" ").last()

                when(colour) {
                    "red" -> redCounts.add(count)
                    "green" -> greenCounts.add(count)
                    "blue" -> blueCounts.add(count)
                }
            }
        }

        return CubeCounts(
            red = redCounts,
            green = greenCounts,
            blue = blueCounts
        )
    }
}