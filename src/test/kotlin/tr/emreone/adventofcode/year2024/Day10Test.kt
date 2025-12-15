package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day10>(false) {
        Resources.resourceAsList("year2024/day10_example.txt")
            .joinToString("\n") part1 36 part2 81
    }

}
