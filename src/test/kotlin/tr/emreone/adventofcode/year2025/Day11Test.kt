package tr.emreone.adventofcode.year2025

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day11>(false) {
        Resources.resourceAsList("year2025/day11_example.txt")
            .joinToString("\n") part1 5 part2 0L
    }

}
