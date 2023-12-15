import Maze.Coordinate.Companion.EAST
import Maze.Coordinate.Companion.NORTH
import Maze.Coordinate.Companion.SOUTH
import Maze.Coordinate.Companion.WEST
import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val maze = Maze.from(input)
        return maze.countStepsToFurthestPoint()
    }

    fun part2(input: List<String>): Int {
        val maze = Maze.from(input)
        return maze.countEnclosedTiles()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 8)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

class Maze(
    val map: List<List<Char>>
) {
    fun start(): Coordinate {
        return map.indexOfFirst { 'S' in it }.let { y -> Coordinate(map[y].indexOf('S'), y) }
    }

    private fun get(coordinate: Coordinate): Char {
        return map[coordinate.y][coordinate.x]
    }

    fun followPipe(): Set<Coordinate> {

        // Code "borrowed" from Todd Ginsberg's solution (https://todd.ginsberg.com/post/advent-of-code/2023/day10/),
        // because it's more "Kotlin" and so much nicer than my first attempt which was very "Java".  Particularly love
        // the use of constants for NORTH, SOUTH, EAST, WEST that allow simple coordinate arithmetic to determine direction

        val path = mutableSetOf(start())

        var current = start().compassPoints().filter { isValid(it) }.first {
            val direction = it - start()
            (get(it) to direction in VALID_MOVES)
        }
        var direction = current - start()

        while (current != start()) {
            path.add(current)
            VALID_MOVES[get(current) to direction]?.let { nextDirection ->
                direction = nextDirection
                current += direction
            }
        }

        return path.toSet()
    }

    fun isValid(coordinate: Coordinate): Boolean {
        return coordinate.y in map.indices && coordinate.x in map[coordinate.y].indices
    }

    fun countStepsToFurthestPoint(): Int {
        return followPipe().size / 2
    }

    fun countEnclosedTiles(): Int {

        // Find area (A) enclosed by pipe using shoelace formula (https://en.wikipedia.org/wiki/Shoelace_formula)
        val area = calculatePipeArea()
        // Number of points on boundary (b)
        val countBoundaryPoints = followPipe().size
        /*
        Then substitute A and b into Pick's theorem (https://en.wikipedia.org/wiki/Pick%27s_theorem) to find the number of interior points (i):
            A = i + b/2 - 1
        Solve for i:
            A + 1 = i + b/2
            A + 1 - b/2 = i
        */

        val interiorPointCount = area + 1 - (countBoundaryPoints / 2)

        return interiorPointCount
    }

    fun calculatePipeArea(): Int {
        val pipe = followPipe().toList()

        var total = 0
        for (i in pipe.indices) {
            val next = if (i + 1 <= pipe.lastIndex) i + 1 else 0
            total += (pipe[i].x * pipe[next].y) - (pipe[i].y * pipe[next].x)
        }

        return (total / 2).absoluteValue
    }

    data class Coordinate(
        val x: Int,
        val y: Int,
    ) {
        fun compassPoints(): Set<Coordinate> {
            return setOf(
                this + NORTH,
                this + SOUTH,
                this + WEST,
                this + EAST
            )
        }

        operator fun plus(other: Coordinate): Coordinate {
            return Coordinate(this.x + other.x, this.y + other.y)
        }

        operator fun minus(other: Coordinate): Coordinate {
            return Coordinate(this.x - other.x, this.y - other.y)
        }

        companion object {
            val NORTH = Coordinate(0, -1)
            val EAST = Coordinate(1, 0)
            val SOUTH = Coordinate(0, 1)
            val WEST = Coordinate(-1, 0)
        }
    }

    companion object {
        fun from(input: List<String>): Maze {
            val maze = mutableListOf<List<Char>>()

            for (line in input) {
                maze.add(line.toCharArray().toList())
            }

            return Maze(maze.toList())
        }

        val VALID_MOVES: Map<Pair<Char, Coordinate>, Coordinate> = mapOf(
            ('|' to SOUTH) to SOUTH,
            ('|' to NORTH) to NORTH,
            ('-' to EAST) to EAST,
            ('-' to WEST) to WEST,
            ('L' to WEST) to NORTH,
            ('L' to SOUTH) to EAST,
            ('J' to SOUTH) to WEST,
            ('J' to EAST) to NORTH,
            ('7' to EAST) to SOUTH,
            ('7' to NORTH) to WEST,
            ('F' to WEST) to SOUTH,
            ('F' to NORTH) to EAST
        )
    }
}
