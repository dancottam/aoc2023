fun main() {
    fun part1(input: List<String>): Int {
        val cards = input.map { Card.from(it) }.toList()
        return cards.sumOf { it.score() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

data class Card(
    val winningNumbers: Set<Int>,
    val myNumbers: Set<Int>
) {
    fun score(): Int {
        var score = 0
        repeat(winningNumbers.intersect(myNumbers).size) {
            score = score.times(2).coerceAtLeast(1)
        }
        return score
    }

    companion object {
        fun from(input: String): Card {
            val split = input.split("|")
            val winningNumbers = parseNumbers(split.first().substringAfter(":"))
            val myNumbers = parseNumbers(split.last())
            return Card(
                winningNumbers = winningNumbers,
                myNumbers = myNumbers
            )
        }

        private fun parseNumbers(numbers: String): Set<Int> {
            return numbers.trim().split("\\s+".toRegex()).map(String::toInt).toSet()
        }
    }

}
