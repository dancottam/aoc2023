import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day06Tests {

    @Test
    fun `Parse races`() {

        val input = """
            Time:      7  15   30
            Distance:  9  40  200
        """.trimIndent().lines()

        val races = Races.from(input)

        assertThat(races).isEqualTo(listOf(
            Race(
                time = 7,
                recordDistance = 9
            ),
            Race(
                time = 15,
                recordDistance = 40
            ),
            Race(
                time = 30,
                recordDistance = 200
            )
        ))
    }

    @Test
    fun `Travels 0mm if button pressed for 0ms`() {
        val race = Race(time = 7, recordDistance = 9)

        assertThat(race.distanceTravelledAfterPressingFor(0)).isEqualTo(0)
    }

    @Test
    fun `Pressing button for entire race means 0 distance travelled`() {
        val race = Race(time = 7, recordDistance = 9)

        assertThat(race.distanceTravelledAfterPressingFor(7)).isEqualTo(0)
    }

    @ParameterizedTest
    @CsvSource(
        "1,6",
        "2,10",
        "3,12",
        "4,12",
        "5,10",
        "6,6"
    )
    fun `Calculate travel distance after button release`(pressDuration: Long, expectedDistance: Long) {

        val race = Race(time = 7, recordDistance = 9)

        assertThat(race.distanceTravelledAfterPressingFor(pressDuration)).isEqualTo(expectedDistance)
    }

    @Test
    fun `Find record-beating presses`() {
        val race = Race(time = 7, recordDistance = 9)

        assertThat(race.findRecordBeatingPresses()).isEqualTo(listOf<Long>(2, 3, 4, 5))
    }

    @Test
    fun `Parse single race ignoring spaces`() {
        val input = """
            Time:      7  15   30
            Distance:  9  40  200
        """.trimIndent().lines()

        val race = Race.from(input)

        assertThat(race).isEqualTo(Race(
            time = 71530,
            recordDistance = 940200
        ))
    }
}
