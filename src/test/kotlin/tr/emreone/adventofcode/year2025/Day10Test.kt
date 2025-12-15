package tr.emreone.adventofcode.year2025

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day10>(false) {
        Resources.resourceAsList("year2025/day10_example.txt")
            .joinToString("\n") part1 7 part2 33L
    }

}
