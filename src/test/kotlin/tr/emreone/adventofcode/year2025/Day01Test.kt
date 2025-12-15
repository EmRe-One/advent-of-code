package tr.emreone.adventofcode.year2025

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day01>(false) {
        Resources.resourceAsList("year2025/day01_example.txt")
            .joinToString("\n") part1 3 part2 6
    }

}
