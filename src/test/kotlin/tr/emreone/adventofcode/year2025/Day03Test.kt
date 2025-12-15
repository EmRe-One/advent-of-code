package tr.emreone.adventofcode.year2025

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day03>(false) {
        Resources.resourceAsList("year2025/day03_example.txt")
            .joinToString("\n") part1 357 part2 3_121_910_778_619L
    }

}
