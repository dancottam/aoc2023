fun main() {
    fun part1(input: List<String>): Int {
        val races = Races.from(input)
        return races.map{it.findRecordBeatingPresses().size}.reduce(Int::times)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

class Races {
    companion object {
        fun from(input: List<String>): List<Race> {
            val times = input.first().substringAfter("Time:").trim().split("\\s+".toRegex()).map(String::toInt)
            val distances = input.last().substringAfter("Distance:").trim().split("\\s+".toRegex()).map(String::toInt)

            return times.withIndex().map { Race(it.value, distances[it.index]) }
        }
    }

}

data class Race(
    val time: Int,
    val recordDistance: Int
) {
    fun distanceTravelledAfterPressingFor(pressFor: Int): Int {
        return (time - pressFor) * pressFor
    }

    fun findRecordBeatingPresses(): List<Int> {
        return (0 ..time).filter { distanceTravelledAfterPressingFor(it) > recordDistance }.toList()
    }

}