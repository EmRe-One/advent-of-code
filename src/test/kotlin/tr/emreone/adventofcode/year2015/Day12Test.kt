package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day12>(false) {
        Resources.resourceAsList("year2015/day12_example.txt")
            .joinToString("\n") part1 6 part2 6
    }

}
