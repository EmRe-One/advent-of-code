package tr.emreone.adventofcode.year2023

import org.junit.jupiter.api.Test
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

internal class Day18Test {

    @Test
    fun `execute_tests`() {
        solve<Day18>(false) {
            Resources.resourceAsList("year2023/day18_example.txt")
                .joinToString("\n") part1 62 part2 952_408_144_115L
        }
    }

}
