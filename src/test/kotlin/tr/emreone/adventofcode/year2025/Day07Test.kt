package tr.emreone.adventofcode.year2025

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day07>(false) {
        Resources.resourceAsList("year2025/day07_example.txt")
            .joinToString("\n") part1 21 part2 40
    }

}
