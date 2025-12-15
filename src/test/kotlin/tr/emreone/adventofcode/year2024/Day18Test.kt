package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day18>(false) {
        Resources.resourceAsList("year2024/day18_example.txt")
            .joinToString("\n") part1 22 part2 "6,1"
    }

}
