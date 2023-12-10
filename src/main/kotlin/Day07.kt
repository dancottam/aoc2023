fun main() {
    fun part1(input: List<String>): Int {
        val hands = input.map { Hand.from(it) }.sorted()

        return hands.withIndex().sumOf { (it.index + 1) * it.value.bid }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 6440)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

data class Hand(
    val cards: String,
    val bid: Int
) : Comparable<Hand> {
    fun type(): Type {
        val cardCounts = cards.groupingBy { it }.eachCount().values.sortedDescending()
        return Type.from(cardCounts)
    }

    enum class Type(val rank: Int, val cardCounts: List<Int>) {
        FIVE_OF_A_KIND(6, listOf(5)),
        FOUR_OF_A_KIND(5, listOf(4, 1)),
        FULL_HOUSE(4, listOf(3, 2)),
        THREE_OF_A_KIND(3, listOf(3, 1, 1)),
        TWO_PAIR(2, listOf(2, 2, 1)),
        ONE_PAIR(1, listOf(2, 1, 1, 1)),
        HIGH_CARD(0, listOf(1, 1, 1, 1, 1)),
        ;

        companion object {
            fun from(cardCounts: List<Int>): Type {
                return entries.first { it.cardCounts == cardCounts }
            }
        }
    }

    companion object {
        fun from(input: String): Hand {
            val (cards, bid) = input.trim().split("\\s+".toRegex())
            return Hand(cards, bid.toInt())
        }

        const val CARD_STRENGTH = "23456789TJQKA"
    }

    override fun compareTo(other: Hand): Int {
        val rankComparator = compareBy<Hand> { it.type().rank }
            .thenBy { strength(it.cards[0]) }
            .thenBy { strength(it.cards[1]) }
            .thenBy { strength(it.cards[2]) }
            .thenBy { strength(it.cards[3])}
            .thenBy { strength(it.cards[4]) }
        return rankComparator.compare(this, other)
    }

    private fun strength(card: Char): Int {
        return CARD_STRENGTH.indexOf(card)
    }
}