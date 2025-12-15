package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day13>(false) {
        Resources.resourceAsList("year2024/day13_example.txt")
            .joinToString("\n") part1 480 part2 875_318_608_908L
    }

}
