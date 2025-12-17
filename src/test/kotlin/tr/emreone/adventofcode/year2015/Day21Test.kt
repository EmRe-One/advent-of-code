package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day21>(false) {
        Resources.resourceAsList("year2015/day21_example.txt")
            .joinToString("\n") part1 8 part2 Long.MIN_VALUE
    }

}
