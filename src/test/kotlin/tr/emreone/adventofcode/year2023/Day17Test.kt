package tr.emreone.adventofcode.year2023

import org.junit.jupiter.api.Test
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

internal class Day17Test {

    @Test
    fun `execute_tests`() {
        solve<Day17>(false) {
            Resources.resourceAsList("year2023/day17_example.txt")
                .joinToString("\n") part1 102 part2 94
        }
    }

}
