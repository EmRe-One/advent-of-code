package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day03>(false) {
        Resources.resourceAsList("year2024/day03_1example.txt")
            .joinToString("\n") part1 161

        Resources.resourceAsList("year2024/day03_2example.txt")
            .joinToString("\n") part2 48
    }

}
