package tr.emreone.adventofcode23.days

import org.junit.jupiter.api.Test
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

internal class Day09Test {

    @Test
    fun `execute_tests`() {
        solve<Day09>(true) {
            Resources.resourceAsList("day09_example.txt")
                .joinToString("\n") part1 114 part2 2
        }
    }

}
