package tr.emreone.adventofcode.year2016

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day13>(false) {
        Resources.resourceAsList("year2016/day13_example.txt")
            .joinToString("\n") part1 -1 part2 -1
    }

}
