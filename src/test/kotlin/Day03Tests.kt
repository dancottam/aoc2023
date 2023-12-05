import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day03Tests {

    @Test
    fun `Find all Points adjacent to a given Point`() {
        val point = Point(3, 3)
        assertThat(point.neighbours()).isEqualTo(setOf(
            Point(2, 2),
            Point(3, 2),
            Point(4, 2),
            Point(2, 3),
            Point(4, 3),
            Point(2, 4),
            Point(3, 4),
            Point(4, 4)
        ))
    }

    @Test
    fun `EnginePart is constructed from chars and points`() {
        val part = EnginePart.Builder()
            .addDigitWithPoint('1', Point(1, 1))
            .addDigitWithPoint('2', Point(2, 1))
            .addDigitWithPoint('3', Point(3, 1))
            .build()

        assertThat(part.number).isEqualTo(123)
    }

    @Test
    fun `Find all parts`() {
        val schematic = """
            ..12.
            .34..
            ...56
        """.trimIndent().lines()

        val engine = Engine.from(schematic)

        assertThat(engine.parts).isEqualTo(listOf(
            EnginePart(12, setOf(Point(2, 0), Point(3, 0))),
            EnginePart(34, setOf(Point(1, 1), Point(2, 1))),
            EnginePart(56, setOf(Point(3, 2), Point(4, 2)))
        ))
    }

    @Test
    fun `Find all symbols`() {
        val schematic = """
            ..@..
            .#...
            ...$.
        """.trimIndent().lines()

        val engine = Engine.from(schematic)

        assertThat(engine.symbols).isEqualTo(setOf(
            EngineSymbol('@', Point(2, 0)),
            EngineSymbol('#', Point(1, 1)),
            EngineSymbol('$', Point(3, 2))
        ))
    }

    @Test
    fun `Find all parts adjacent to symbols`() {
        val schematic = """
            ..12*
            @34..
            ...56
        """.trimIndent().lines()

        val engine = Engine.from(schematic)

        assertThat(engine.partsAdjacentToSymbols()).isEqualTo(listOf(
            EnginePart(12, setOf(Point(2, 0), Point(3, 0))),
            EnginePart(34, setOf(Point(1, 1), Point(2, 1))),
        ))
    }

    @Test
    fun `Find all gears`() {
        val schematic = """
            ..12*
            @34..
            ..*56
        """.trimIndent().lines()

        val engine = Engine.from(schematic)

        assertThat(engine.gears()).isEqualTo(setOf(
            EngineGear(listOf(
                EnginePart(34, setOf(Point(1, 1), Point(2, 1))),
                EnginePart(56, setOf(Point(3, 2), Point(4, 2))),
            ))
        ))
    }

    @Test
    fun `Calculate gear ratio`() {
        val schematic = """
            ..12*
            @34..
            ..*56
        """.trimIndent().lines()

        val engine = Engine.from(schematic)
        val gear = engine.gears().first()
        assertThat(gear.ratio()).isEqualTo(1904)
    }

}