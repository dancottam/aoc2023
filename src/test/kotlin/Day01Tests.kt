import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day01Tests {

    @Test
    fun `Find first and last digits`() {
        val calibrationValue = Part1ValueParser().parse("1abc2")
        assertThat(calibrationValue).isEqualTo(12)
    }

    @Test
    fun `Single digit treated as both first and last`() {
        val calibrationValue = Part1ValueParser().parse("treb7uchet")
        assertThat(calibrationValue).isEqualTo(77)
    }

    @Test
    fun `Part 1 sum of calibration values`() {
        val input = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
            """.trimIndent().lines()
        val calibrationValues = CalibrationValues(input, Part1ValueParser())
        assertThat(calibrationValues.sum()).isEqualTo(142)
    }

    @ParameterizedTest
    @CsvSource(
            "two1nine,29",
            "eightwothree,83",
            "abcone2threexyz,13",
            "xtwone3four,24",
            "4nineeightseven2,42",
            "zoneight234,14",
            "7pqrstsixteen,76"
    )
    fun `Converts spelled out numbers to digits`(input: String, expected: Int) {
        val calibrationValue = Part2ValueParser().parse(input)
        assertThat(calibrationValue).isEqualTo(expected)
    }

    @Test
    fun `Part 2 sum of calibration values`() {
        val input = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen
            """.trimIndent().lines()
        val calibrationValues = CalibrationValues(input, Part2ValueParser())
        assertThat(calibrationValues.sum()).isEqualTo(281)
    }

}