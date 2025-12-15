package tr.emreone.adventofcode.year2023

import org.junit.jupiter.api.Test
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

internal class Day13Test {

    @Test
    fun `execute_tests`() {
        solve<Day13>(false) {
            Resources.resourceAsList("year2023/day13_example.txt")
                .joinToString("\n") part1 405 part2 400
        }
    }

}
