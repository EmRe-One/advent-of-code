package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day14>(false) {
        Resources.resourceAsList("year2015/day14_example.txt")
            .joinToString("\n") part1 1_120 part2 689
    }

}
