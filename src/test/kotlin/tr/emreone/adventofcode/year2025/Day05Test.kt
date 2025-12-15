package tr.emreone.adventofcode.year2025

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day05>(false) {
        Resources.resourceAsList("year2025/day05_example.txt")
            .joinToString("\n") part1 3 part2 14L
    }

}
