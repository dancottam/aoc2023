fun main() {
    fun part1(input: List<String>): Int {
        val hands = input.map { Hand.from(it) }.sortedWith(Hand.SIMPLE_RANK_COMPARATOR)

        return hands.withIndex().sumOf { (it.index + 1) * it.value.bid }
    }

    fun part2(input: List<String>): Int {
        val hands = input.map { Hand.from(it) }.sortedWith(Hand.JOKER_RANK_COMPARATOR)

        return hands.withIndex().sumOf { (it.index + 1) * it.value.bid }
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
) {
    fun type(): Type {
        val cardCounts = countCards(cards)
        return Type.from(cardCounts)
    }

    fun typeWithJokers(): Type {
        val nonJokers = cards.filterNot { it == 'J' }
        val numJokers = cards.length - nonJokers.length
        if (numJokers == 0 || numJokers == 5) {
            return type()
        }
        val cardCounts = countCards(nonJokers)
        return Type.from(listOf(cardCounts.first() + numJokers, *cardCounts.drop(1).toTypedArray()))
    }

    private fun countCards(nonJokers: String): List<Int> {
        return nonJokers.groupingBy { it }.eachCount().values.sortedDescending()
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

        private const val CARD_STRENGTH = "23456789TJQKA"
        private const val JOKER_CARD_STRENGTH = "J23456789TQKA"

        val SIMPLE_RANK_COMPARATOR = compareBy<Hand> { it.type().rank }
            .thenBy { CARD_STRENGTH.indexOf(it.cards[0]) }
            .thenBy { CARD_STRENGTH.indexOf(it.cards[1]) }
            .thenBy { CARD_STRENGTH.indexOf(it.cards[2]) }
            .thenBy { CARD_STRENGTH.indexOf(it.cards[3]) }
            .thenBy { CARD_STRENGTH.indexOf(it.cards[4]) }

        val JOKER_RANK_COMPARATOR = compareBy<Hand> { it.typeWithJokers().rank }
            .thenBy { JOKER_CARD_STRENGTH.indexOf(it.cards[0]) }
            .thenBy { JOKER_CARD_STRENGTH.indexOf(it.cards[1]) }
            .thenBy { JOKER_CARD_STRENGTH.indexOf(it.cards[2]) }
            .thenBy { JOKER_CARD_STRENGTH.indexOf(it.cards[3]) }
            .thenBy { JOKER_CARD_STRENGTH.indexOf(it.cards[4]) }

        fun from(input: String): Hand {
            val (cards, bid) = input.trim().split("\\s+".toRegex())
            return Hand(cards, bid.toInt())
        }
    }

}