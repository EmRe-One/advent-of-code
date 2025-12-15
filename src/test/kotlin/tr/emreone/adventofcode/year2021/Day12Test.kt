package tr.emreone.adventofcode.year2021

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day12>(false) {
        Resources.resourceAsList("year2021/day12_example.txt")
            .joinToString("\n") part1 -1 part2 -1
    }

}
