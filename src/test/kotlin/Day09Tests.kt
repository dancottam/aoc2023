import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day09Tests {

    @Test
    fun `Extrapolate next value`() {
        val sequence = ReportValues(listOf(0, 3, 6, 9, 12, 15))

        assertThat(sequence.nextValue()).isEqualTo(18)
    }

    @Test
    fun `Sum extrapolated next values`() {
        val input = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
        """.trimIndent().lines()

        val sumValues = extrapolateNextValuesAndSum(input)

        assertThat(sumValues).isEqualTo(114)
    }

    @Test
    fun `Extrapolate previous value`() {
        val sequence = ReportValues(listOf(0, 3, 6, 9, 12, 15))

        assertThat(sequence.previousValue()).isEqualTo(-3)
    }

    @Test
    fun `Sum extrapolated previous values`() {
        val input = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
        """.trimIndent().lines()

        val sumValues = extrapolatePreviousValuesAndSum(input)

        assertThat(sumValues).isEqualTo(2)
    }
}
