package tr.emreone.adventofcode.year2025

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day08>(false) {
        Resources.resourceAsList("year2025/day08_example.txt")
            .joinToString("\n") part1 40 part2 25_272
    }

}
