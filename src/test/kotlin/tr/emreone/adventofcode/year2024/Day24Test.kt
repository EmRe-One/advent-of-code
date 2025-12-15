package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day24>(false) {
        Resources.resourceAsList("year2024/day24_example.txt")
            .joinToString("\n") part1 2_024L part2 ""
    }

}
