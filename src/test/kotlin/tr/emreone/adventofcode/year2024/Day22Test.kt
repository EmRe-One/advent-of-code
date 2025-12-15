package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day22>(false) {
        Resources.resourceAsList("year2024/day22_example.txt")
            .joinToString("\n") part1 37_327_623 part2 24L
    }

}
