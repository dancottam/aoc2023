import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day04Tests {

    @Test
    fun `Parse single card`() {
        val input = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val card = Card.from(input)

        assertThat(card).isEqualTo(Card(
            winningNumbers = setOf(41, 48, 83, 86, 17),
            myNumbers = setOf(83, 86, 6, 31, 17, 9, 48, 53),
        ))
    }

    @Test
    fun `Calculate score`() {
        val input = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53"
        val card = Card.from(input)

        assertThat(card.score()).isEqualTo(8)
    }

}