import Maze.Coordinate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day10Tests {

    @Test
    fun `Parse maze`() {
        val input = """
            ...
            .S.
            ...
        """.trimIndent().lines()

        val maze = Maze.from(input)

        assertThat(maze.map).isEqualTo(listOf(
            listOf('.', '.', '.'),
            listOf('.', 'S', '.'),
            listOf('.', '.', '.'),
        ))
    }

    @Test
    fun `Find start`() {
        val input = """
            .....
            .S-7.
            .|.|.
            .L-J.
            .....
        """.trimIndent().lines()

        val maze = Maze.from(input)
        assertThat(maze.start()).isEqualTo(Coordinate(1, 1))
    }

    @Test
    fun `Coordinate is valid`() {
        val input = """
            ...
            .S.
            ...
        """.trimIndent().lines()

        val maze = Maze.from(input)

        assertThat(maze.isValid(Coordinate(1, 1))).isTrue()
    }

    @Test
    fun `Coordinate is not valid`() {
        val input = """
            ...
            .S.
            ...
        """.trimIndent().lines()

        val maze = Maze.from(input)

        assertThat(maze.isValid(Coordinate(3, 0))).isFalse()
    }

    @Test
    fun `Follow pipe`() {
        val input = """
            .....
            .S-7.
            .|.|.
            .L-J.
            .....
        """.trimIndent().lines()

        val maze = Maze.from(input)

        assertThat(maze.followPipe()).isEqualTo(setOf(
            Coordinate(1, 1),
            Coordinate(2, 1),
            Coordinate(3, 1),
            Coordinate(3, 2),
            Coordinate(3, 3),
            Coordinate(2, 3),
            Coordinate(1, 3),
            Coordinate(1, 2),
        ))
    }

    @Test
    fun `Count steps to furthest point from start`() {
        val input = """
            .....
            .S-7.
            .|.|.
            .L-J.
            .....
        """.trimIndent().lines()

        val maze = Maze.from(input)

        assertThat(maze.countStepsToFurthestPoint()).isEqualTo(4)
    }
}
