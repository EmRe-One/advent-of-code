package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day02>(false) {
        Resources.resourceAsList("year2024/day02_example.txt")
            .joinToString("\n") part1 2 part2 4
    }

}
