fun main() {
    fun part1(input: List<String>): Int {
        val calibrationValues = CalibrationValues(input, Part1ValueParser())
        return calibrationValues.sum()
    }

    fun part2(input: List<String>): Int {
        val calibrationValues = CalibrationValues(input, Part2ValueParser())
        return calibrationValues.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

class CalibrationValues(input: List<String>, valueParser: ValueParser) {

    val calibrationValues: List<Int> = input.map { valueParser.parse(it) }.toList()

    fun sum(): Int {
        return calibrationValues.sum()
    }
}

interface ValueParser {
    fun parse(input: String): Int
}

open class Part1ValueParser: ValueParser {
    override fun parse(input: String): Int {
        val digits = input.filter{ it.isDigit() }
        val first = digits.first()
        val last = digits.last()
        return "$first$last".toInt()
    }

}

class Part2ValueParser: Part1ValueParser() {
    override fun parse(input: String): Int {
        return super.parse(input
                .replace("one", "one1one")
                .replace("two", "two2two")
                .replace("three", "three3three")
                .replace("four", "four4four")
                .replace("five", "five5five")
                .replace("six", "six6six")
                .replace("seven", "seven7seven")
                .replace("eight", "eight8eight")
                .replace("nine", "nine9nine")
        )
    }

}
