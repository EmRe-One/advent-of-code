package tr.emreone.adventofcode.year2023

import org.junit.jupiter.api.Test
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

internal class Day09Test {

    @Test
    fun `execute_tests`() {
        solve<Day09>(true) {
            Resources.resourceAsList("year2023/day09_example.txt")
                .joinToString("\n") part1 114 part2 2
        }
    }

}
