fun main() {
    fun part1(input: List<String>): Int {
        return extrapolateNextValuesAndSum(input)
    }

    fun part2(input: List<String>): Int {
        return extrapolatePreviousValuesAndSum(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

fun extrapolateNextValuesAndSum(input: List<String>): Int {
    val newValues = input.map { ReportValues(it.split("\\s+".toRegex()).map(String::toInt)) }
    return newValues.sumOf { it.nextValue() }
}

fun extrapolatePreviousValuesAndSum(input: List<String>): Int {
    val newValues = input.map { ReportValues(it.split("\\s+".toRegex()).map(String::toInt)) }
    return newValues.sumOf { it.previousValue() }
}

data class ReportValues(
    val sequence: List<Int>
) {
    fun nextValue(): Int {
        return extrapolateNext(sequence)
    }

    private fun extrapolateNext(sequence: List<Int>): Int {
        if (sequence.all { it == 0 }) {
            return 0
        } else {
            val diffs = sequence.windowed(2, 1).map { it[1] - it[0] }
            return sequence.last() + extrapolateNext(diffs)
        }
    }

    fun previousValue(): Int {
        return extrapolatePrevious(sequence)
    }

    private fun extrapolatePrevious(sequence: List<Int>): Int {
        if (sequence.all { it == 0 }) {
            return 0
        } else {
            val diffs = sequence.windowed(2, 1).map { it[1] - it[0] }
            return sequence.first() - extrapolatePrevious(diffs)
        }
    }

}
