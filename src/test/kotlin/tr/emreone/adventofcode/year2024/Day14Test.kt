package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day14>(false) {
        Resources.resourceAsList("year2024/day14_example.txt")
            .joinToString("\n") part1 12 part2 0
    }

}
