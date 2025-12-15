package tr.emreone.adventofcode23.days

import org.junit.jupiter.api.Test
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

internal class Day12Test {

    @Test
    fun `execute_tests`() {
        solve<Day12>(false) {
            Resources.resourceAsList("day12_example.txt")
                .joinToString("\n") part1 21 part2 525_152
        }
    }

}
