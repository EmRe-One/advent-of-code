package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day18>(false) {
        Resources.resourceAsList("year2015/day18_example.txt")
            .joinToString("\n") part1 4 part2 14
    }

}
