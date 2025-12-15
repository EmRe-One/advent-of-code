package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day02>(false) {
        Resources.resourceAsList("year2015/day02_example.txt")
            .joinToString("\n") part1 101 part2 48
    }

}
