package tr.emreone.adventofcode.year2023

import org.junit.jupiter.api.Test
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

internal class Day10Test {

    @Test
    fun `execute_tests`() {
        solve<Day10>(false) {
            Resources.resourceAsList("year2023/day10_1_example.txt")
                .joinToString("\n") part1 8
        }
        solve<Day10>(false) {
            Resources.resourceAsList("year2023/day10_2_example.txt")
                .joinToString("\n") part2 10
        }
    }

}
