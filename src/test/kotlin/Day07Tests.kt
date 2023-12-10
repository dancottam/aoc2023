import Hand.Type.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day07Tests {

    @Test
    fun `Parse hand`() {
        val hand = Hand.from("23456 765")
        assertThat(hand).isEqualTo(Hand("23456", 765))
    }

    @Test
    fun `High card`() {
        val hand = Hand("23456", 0)
        assertThat(hand.type()).isEqualTo(HIGH_CARD)
    }

    @Test
    fun `One pair`() {
        val hand = Hand("A23A4", 0)
        assertThat(hand.type()).isEqualTo(ONE_PAIR)
    }

    @Test
    fun `Two pair`() {
        val hand = Hand("23432", 0)
        assertThat(hand.type()).isEqualTo(TWO_PAIR)
    }

    @Test
    fun `Three of a kind`() {
        val hand = Hand("TTT98", 0)
        assertThat(hand.type()).isEqualTo(THREE_OF_A_KIND)
    }

    @Test
    fun `Full house`() {
        val hand = Hand("23332", 0)
        assertThat(hand.type()).isEqualTo(FULL_HOUSE)
    }

    @Test
    fun `Four of a kind`() {
        val hand = Hand("AA8AA", 0)
        assertThat(hand.type()).isEqualTo(FOUR_OF_A_KIND)
    }

    @Test
    fun `Five of a kind`() {
        val hand = Hand("AAAAA", 0)
        assertThat(hand.type()).isEqualTo(FIVE_OF_A_KIND)
    }

    @Test
    fun `Rank hands`() {
        val input = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent().lines()

        val hands = input.map { Hand.from(it) }.sortedWith(Hand.SIMPLE_RANK_COMPARATOR)

        assertThat(hands).isEqualTo(listOf(
            Hand("32T3K", 765),
            Hand("KTJJT", 220),
            Hand("KK677", 28),
            Hand("T55J5", 684),
            Hand("QQQJA", 483)
        ))
    }

    @Test
    fun `Calculate winnings`() {
        val input = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent().lines()

        val hands = input.map { Hand.from(it) }.sortedWith(Hand.SIMPLE_RANK_COMPARATOR)

        val winnings = hands.withIndex().sumOf { (it.index + 1) * it.value.bid }

        assertThat(winnings).isEqualTo(6440)
    }

    @Test
    fun `Type with jokers`() {
        val hand = Hand("QQQJA", 483)
        assertThat(hand.typeWithJokers()).isEqualTo(FOUR_OF_A_KIND)
    }

    @Test
    fun `Rank hands with jokers`() {
        val input = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent().lines()

        val hands = input.map { Hand.from(it) }.sortedWith(Hand.JOKER_RANK_COMPARATOR)

        assertThat(hands).isEqualTo(listOf(
            Hand("32T3K", 765),
            Hand("KK677", 28),
            Hand("T55J5", 684),
            Hand("QQQJA", 483),
            Hand("KTJJT", 220),
        ))
    }

    @Test
    fun `Calculate winnings wiht jokers`() {
        val input = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent().lines()

        val hands = input.map { Hand.from(it) }.sortedWith(Hand.JOKER_RANK_COMPARATOR)

        val winnings = hands.withIndex().sumOf { (it.index + 1) * it.value.bid }

        assertThat(winnings).isEqualTo(5905)
    }
}
