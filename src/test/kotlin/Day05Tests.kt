import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day05Tests {

    @Test
    fun `Parse seeds`() {
        val input = "seeds: 79 14 55 13"
        val seeds = Seeds.from(input)
        assertThat(seeds).isEqualTo(listOf(79L, 14L, 55L, 13L))
    }

    @Test
    fun `Parse lookup map`() {
        val input = """
            seed-to-soil map:
            50 98 2
            52 50 48
        """.trimIndent().lines()

        val lookup = Lookup.from(input)
        assertThat(lookup).isEqualTo(Lookup(
            srcCat = "seed",
            destCat = "soil",
            mapping = mapOf(
                LongRange(98, 99) to LongRange(50, 51),
                LongRange(50, 97) to LongRange(52, 99)
            )
        ))
    }

    @Test
    fun `Lookup dest from mapped src`() {
        val input = """
            seed-to-soil map:
            50 98 2
            52 50 48
        """.trimIndent().lines()

        val lookup = Lookup.from(input)
        assertThat(lookup.findDest(79)).isEqualTo(81)
    }

    @Test
    fun `Lookup dest from unmapped src`() {
        val input = """
            seed-to-soil map:
            50 98 2
            52 50 48
        """.trimIndent().lines()

        val lookup = Lookup.from(input)
        assertThat(lookup.findDest(14)).isEqualTo(14)
    }

    @Test
    fun `Create almanac`() {
        val input = """
            seeds: 79 14 55 13

            seed-to-soil map:
            50 98 2
            52 50 48
            
            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15
        """.trimIndent().lines()

        val almanac = Almanac.from(input)

        assertThat(almanac).isEqualTo(Almanac(
            seeds = listOf(79, 14, 55, 13),
            seedRanges = listOf(
                LongRange(79, 92),
                LongRange(55, 67)
            ),
            lookups = listOf(
                Lookup(
                    srcCat = "seed",
                    destCat = "soil",
                    mapping = mapOf(
                        LongRange(98, 99) to LongRange(50, 51),
                        LongRange(50, 97) to LongRange(52, 99)
                    )
                ),
                Lookup(
                    srcCat = "soil",
                    destCat = "fertilizer",
                    mapping = mapOf(
                        LongRange(15, 51) to LongRange(0, 36),
                        LongRange(52, 53) to LongRange(37, 38),
                        LongRange(0, 14) to LongRange(39, 53)
                    )
                )
            )
        ))
    }

    @Test
    fun `Find location for seed`() {
        val input = """
            seeds: 79 14 55 13

            seed-to-soil map:
            50 98 2
            52 50 48

            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15

            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4

            water-to-light map:
            88 18 7
            18 25 70

            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13

            temperature-to-humidity map:
            0 69 1
            1 0 69

            humidity-to-location map:
            60 56 37
            56 93 4
        """.trimIndent().lines()

        val almanac = Almanac.from(input)

        assertThat(almanac.findLocationForSeed(79)).isEqualTo(82)
    }

    @Test
    fun `Find lowest location`() {
        val input = """
            seeds: 79 14 55 13

            seed-to-soil map:
            50 98 2
            52 50 48

            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15

            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4

            water-to-light map:
            88 18 7
            18 25 70

            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13

            temperature-to-humidity map:
            0 69 1
            1 0 69

            humidity-to-location map:
            60 56 37
            56 93 4
        """.trimIndent().lines()

        val almanac = Almanac.from(input)

        assertThat(almanac.lowestLocation()).isEqualTo(35)
    }

    @Test
    fun `Parse seed range`() {
        val input = "seeds: 79 14 55 13"
        val seedRanges = Seeds.rangesFrom(input)
        assertThat(seedRanges).isEqualTo(listOf(
            LongRange(79, 92),
            LongRange(55, 67)
        ))
    }

    @Test
    fun `Lookup src from mapped dest`() {
        val input = """
            seed-to-soil map:
            50 98 2
            52 50 48
        """.trimIndent().lines()

        val lookup = Lookup.from(input)
        assertThat(lookup.findSrc(81)).isEqualTo(79)
    }

    @Test
    fun `Lookup src from unmapped dest`() {
        val input = """
            seed-to-soil map:
            50 98 2
            52 50 48
        """.trimIndent().lines()

        val lookup = Lookup.from(input)
        assertThat(lookup.findSrc(14)).isEqualTo(14)
    }

    @Test
    fun `Find lowest location for seed ranges`() {
        val input = """
            seeds: 79 14 55 13

            seed-to-soil map:
            50 98 2
            52 50 48

            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15

            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4

            water-to-light map:
            88 18 7
            18 25 70

            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13

            temperature-to-humidity map:
            0 69 1
            1 0 69

            humidity-to-location map:
            60 56 37
            56 93 4
        """.trimIndent().lines()

        val almanac = Almanac.from(input)

        assertThat(almanac.lowestLocationForSeedRanges()).isEqualTo(46)
    }
}
