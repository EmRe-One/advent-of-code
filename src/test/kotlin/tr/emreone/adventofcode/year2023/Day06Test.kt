package tr.emreone.adventofcode.year2023

import org.junit.jupiter.api.Test
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

internal class Day06Test {

    @Test
    fun `execute_tests`() {
        solve<Day06>(true) {
            Resources.resourceAsList("year2023/day06_example.txt")
                .joinToString("\n") part1 288 part2 71503L
        }
    }

}
