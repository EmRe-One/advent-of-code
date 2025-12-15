package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day01>(false) {
        Resources.resourceAsList("year2024/day01_example.txt")
            .joinToString("\n") part1 11 part2 31
    }

}
