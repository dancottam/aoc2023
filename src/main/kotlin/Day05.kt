fun main() {
    fun part1(input: List<String>): Long {
        val almanac = Almanac.from(input)
        return almanac.lowestLocation()
    }

    fun part2(input: List<String>): Long {
        val almanac = Almanac.from(input)
        return almanac.lowestLocationForSeedRanges()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35.toLong())

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

data class Almanac(
    val seeds: List<Long>,
    val lookups: List<Lookup>,
    val seedRanges: List<LongRange>
) {
    fun findLocationForSeed(seed: Long): Long {
        var result = seed
        for (lookup in lookups) {
            result = lookup.findDest(result)
        }

        return result
    }

    fun lowestLocation(): Long {
        return seeds.map(::findLocationForSeed).min()
    }

    fun lowestLocationForSeedRanges(): Long {
        return generateSequence(0L) { it + 1 }.first {
            var result = it
            for(lookup in lookups.reversed()) {
                result = lookup.findSrc(result)
            }
            seedRanges.any {
                it.contains(result)
            }
        }
    }

    companion object {
        fun from(input: List<String>): Almanac {
            val seeds = Seeds.from(input.first())

            val seedRanges = Seeds.rangesFrom(input.first())

            val lookups = mutableListOf<Lookup>()

            val curLookup = mutableListOf<String>()

            for (line in input.drop(2)) {
                if (line.isBlank()) {
                    lookups.add(Lookup.from(curLookup))
                    curLookup.clear()
                } else {
                    curLookup.add(line)
                }
            }
            if (curLookup.isNotEmpty()) {
                lookups.add(Lookup.from(curLookup))
            }

            return Almanac(
                seeds = seeds,
                seedRanges = seedRanges,
                lookups = lookups,
            )
        }
    }

}

data class Lookup(
    val srcCat: String,
    val destCat: String,
    val mapping: Map<LongRange, LongRange>
) {
    fun findDest(src: Long): Long {
        for ((srcRange, destRange) in mapping) {
            if (srcRange.contains(src)) {
                return destRange.first + (src - srcRange.first)
            }
        }
        return src
    }

    fun findSrc(dest: Long): Long {
        for ((srcRange, destRange) in mapping) {
            if (destRange.contains(dest)) {
                return srcRange.first + (dest - destRange.first)
            }
        }
        return dest
    }

    companion object {
        fun from(input: List<String>): Lookup {
            val mapName = input.first().substringBefore(" map").split("-")
            val srcCat = mapName.first()
            val destCat = mapName.last()
            val mapping = mutableMapOf<LongRange, LongRange>()

            for(line in input.drop(1)) {
                val parts = line.split("\\s+".toRegex())
                val destRangeStart = parts[0].toLong()
                val srcRangeStart = parts[1].toLong()
                val rangeLen = parts[2].toLong()

                val srcRange = LongRange(srcRangeStart, (srcRangeStart+rangeLen - 1))
                val destRange = LongRange(destRangeStart, (destRangeStart+rangeLen - 1))
                mapping[srcRange] = destRange
            }

            return Lookup(
                srcCat = srcCat,
                destCat = destCat,
                mapping = mapping.toMap()
            )
        }
    }
}

class Seeds {
    companion object {
        fun from(input: String): List<Long> {
            return parseSeedNums(input).toList()
        }

        fun rangesFrom(input: String): List<LongRange> {
            val seedRanges = mutableListOf<LongRange>()

            for (rangeDef in parseSeedNums(input).chunked(2)) {
                seedRanges.add(LongRange(rangeDef.first(), rangeDef.first() + rangeDef.last() - 1))
            }

            return seedRanges.toList()
        }

        private fun parseSeedNums(input: String) = input.substringAfter("seeds: ").split("\\s+".toRegex()).map(String::toLong)
    }

}