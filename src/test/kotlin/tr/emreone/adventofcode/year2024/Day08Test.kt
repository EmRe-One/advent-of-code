package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day08>(false) {
        Resources.resourceAsList("year2024/day08_example.txt")
            .joinToString("\n") part1 14 part2 34
    }

}
