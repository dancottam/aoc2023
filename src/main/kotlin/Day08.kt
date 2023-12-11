fun main() {
    fun part1(input: List<String>): Long {
        return countStepsToNavigate("AAA", "ZZZ", input)
    }

    fun part2(input: List<String>): Long {
        return countStepsForAllNodesToReachNodesEndingWith('A', 'Z', input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 2L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}

fun countStepsToNavigate(from: String, to: String, instructions: List<String>): Long {
    val directions = Directions(instructions.first())
    val desertMap = DesertMap.from(instructions.drop(2))

    return desertMap.navigate(from, to, directions)
}

fun countStepsForAllNodesToReachNodesEndingWith(fromEndingWith: Char, toEndingWith: Char, instructions: List<String>): Long {
    val directions = Directions(instructions.first())
    val desertMap = DesertMap.from(instructions.drop(2))

    return desertMap.navigateAllEndingWithToNodesEndingWith(fromEndingWith, toEndingWith, directions)
}

class DesertMap(
    val nodes: Map<String, Pair<String, String>>
) {
    fun navigate(from: String, to: String, directions: Directions): Long {
        var steps = 0L
        var curNode = from

        while (curNode.endsWith(to).not()) {
            curNode = turn(directions.next(), curNode)
            steps++
        }

        return steps
    }

    fun navigateAllEndingWithToNodesEndingWith(fromEndingWith: Char, toEndingWith: Char, directions: Directions): Long {

        val allSteps = mutableListOf<Long>()
        for (startingNode in nodes.keys.filter { it.endsWith(fromEndingWith) }) {
            allSteps.add(navigate(startingNode, toEndingWith.toString(), directions))
        }

        return allSteps.reduce { prev, next -> prev.lcm(next) }
    }

    private fun turn(direction: Char, curNode: String): String {
        return when (direction) {
            'L' -> nodes[curNode]?.first ?: throw NoSuchElementException("Node $curNode does not exist")
            'R' -> nodes[curNode]?.second ?: throw NoSuchElementException("Node $curNode does not exist")
            else -> throw RuntimeException("Invalid direction")
        }
    }

    companion object {
        fun from(input: List<String>): DesertMap {
            val nodes = mutableMapOf<String, Pair<String, String>>()
            for (line in input) {
                val (node, left, right) = line.split(" = (", ", ", ")")
                nodes[node] = Pair(left, right)
            }
            return DesertMap(nodes.toMap())
        }
    }

}

private fun Long.lcm(other: Long): Long = (this * other) / this.gcd(other)

private tailrec fun Long.gcd(other: Long): Long = if (other == 0L) this else other.gcd(this % other)

class Directions(
    private val directionsList: String
) {

    private var cursor = 0

    fun next(): Char {
        if (cursor >= directionsList.length) {
            cursor = 0
        }
        return directionsList[cursor++]
    }

}