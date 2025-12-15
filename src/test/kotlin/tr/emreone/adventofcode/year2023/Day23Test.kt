package tr.emreone.adventofcode.year2023

import org.junit.jupiter.api.Test
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

internal class Day23Test {

    @Test
    fun `execute_tests`() {
        solve<Day23>(false) {
            Resources.resourceAsList("year2023/day23_example.txt")
                .joinToString("\n") part1 94 part2 154
        }
    }

}
