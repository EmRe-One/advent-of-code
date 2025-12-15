package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day12>(false) {
        Resources.resourceAsList("year2024/day12_example.txt")
            .joinToString("\n") part1 1_930 part2 1_206
    }

}
