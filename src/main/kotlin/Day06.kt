fun main() {
    fun part1(input: List<String>): Long {
        val races = Races.from(input)
        return races.map{it.findRecordBeatingPresses().size.toLong()}.reduce(Long::times)
    }

    fun part2(input: List<String>): Long {
        val race = Race.from(input)
        return race.findRecordBeatingPresses().size.toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

class Races {
    companion object {
        fun from(input: List<String>): List<Race> {
            val times = input.first().substringAfter("Time:").trim().split("\\s+".toRegex()).map(String::toLong)
            val distances = input.last().substringAfter("Distance:").trim().split("\\s+".toRegex()).map(String::toLong)

            return times.withIndex().map { Race(it.value, distances[it.index]) }
        }
    }

}

data class Race(
    val time: Long,
    val recordDistance: Long
) {
    fun distanceTravelledAfterPressingFor(pressFor: Long): Long {
        return (time - pressFor) * pressFor
    }

    fun findRecordBeatingPresses(): List<Long> {
        return (0 ..time).filter { distanceTravelledAfterPressingFor(it) > recordDistance }.toList()
    }

    companion object {
        fun from(input: List<String>): Race {
            val time = input.first().substringAfter("Time:").trim().replace("\\s+".toRegex(), "").toLong()
            val distance = input.last().substringAfter("Distance:").trim().replace("\\s+".toRegex(), "").toLong()
            return Race(time, distance)
        }
    }

}