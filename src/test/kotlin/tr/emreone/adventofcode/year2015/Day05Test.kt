package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day05>(false) {
        Resources.resourceAsList("year2015/day05_1example.txt")
            .joinToString("\n") part1 2

        Resources.resourceAsList("year2015/day05_2example.txt")
            .joinToString("\n") part2 2
    }

}
