fun main() {
    fun part1(input: List<String>): Int {
        val cards = Cards.from(input)
        return cards.sumOf { it.score() }
    }

    fun part2(input: List<String>): Int {
        val cards = Cards.from(input)
        return Cards.calculateTotalCards(cards)
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
        repeat(numMatches()) {
            score = score.times(2).coerceAtLeast(1)
        }
        return score
    }

    fun numMatches(): Int {
        return winningNumbers.intersect(myNumbers).size
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

class Cards {
    companion object {

        fun from(input: List<String>): List<Card> {
            return input.map { Card.from(it) }.toList()
        }

        fun calculateTotalCards(cards: List<Card>): Int {
            val cardCounts = List(cards.size) { 1 }.toMutableList()

            for ((index, card) in cards.withIndex()) {
                repeat(card.numMatches()) {
                    cardCounts[index + it + 1] += cardCounts[index]
                }
            }

            return cardCounts.sum()
        }
    }
}
