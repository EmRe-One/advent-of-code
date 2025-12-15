package tr.emreone.adventofcode.year2023

import org.junit.jupiter.api.Test
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

internal class Day04Test {

    @Test
    fun `execute_tests`() {
        solve<Day04>(false) {
            Resources.resourceAsList("year2023/day04_example.txt")
                .joinToString("\n") part1 13 part2 30
        }
    }

}
