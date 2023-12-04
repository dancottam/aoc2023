fun main() {
    fun part1(input: List<String>): Int {
        val engine = Engine.from(input)
        return engine.partsAdjacentToSymbols().sumOf { it.number }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}


data class EnginePart(
    val number: Int,
    val points: Set<Point>,
) {
    fun isAdjacentTo(pointsToCheck: Set<Point>): Boolean {
        return points.any { it.isAdjacentTo(pointsToCheck) }
    }

    class Builder {
        private val digits = mutableListOf<Char>()
        private val points = mutableSetOf<Point>()

        fun build(): EnginePart {
            return EnginePart(digits.joinToString("").toInt(), points.toSet())
        }

        fun addDigitWithPoint(digit: Char, point: Point): Builder {
            digits.add(digit)
            points.add(point)
            return this
        }

        fun containsDigits(): Boolean {
            return digits.isNotEmpty()
        }

        fun reset() {
            digits.clear()
            points.clear()
        }
    }

}

data class EngineSymbol(
    val char: Char,
    val point: Point,
)

data class Point(
    val x: Int,
    val y: Int,
) {
    fun neighbours(): Set<Point> {
        return setOf(
            Point(x - 1, y - 1),
            Point(x, y - 1),
            Point(x + 1, y - 1),
            Point(x - 1, y),
            Point(x + 1, y),
            Point(x - 1, y + 1),
            Point(x, y + 1),
            Point(x + 1, y + 1)
        )
    }

    fun isAdjacentTo(pointsToCheck: Set<Point>): Boolean {
        return neighbours().any { pointsToCheck.contains(it) }
    }

}

data class Engine(
    val parts: List<EnginePart>,
    val symbols: Set<EngineSymbol>,
) {
    fun partsAdjacentToSymbols(): List<EnginePart> {
        return parts.filter { part -> part.isAdjacentTo(symbols.map { it.point }.toSet()) }
    }

    companion object {
        fun from(schematic: List<String>): Engine {

            val parts = mutableListOf<EnginePart>()
            val symbols = mutableSetOf<EngineSymbol>()

            val partBuilder = EnginePart.Builder()

            for ((y, line) in schematic.withIndex()) {
                for ((x, char) in line.withIndex()) {
                    if (char.isDigit()) {
                        partBuilder.addDigitWithPoint(char, Point(x, y))
                    } else {
                        if (partBuilder.containsDigits()) {
                            parts.add(partBuilder.build())
                            partBuilder.reset()
                        }
                        if (char != '.') {
                            symbols.add(EngineSymbol(char, Point(x, y)))
                        }
                    }
                }
                if (partBuilder.containsDigits()) {
                    parts.add(partBuilder.build())
                    partBuilder.reset()
                }
            }

            return Engine(parts.toList(), symbols)
        }
    }
}
