package tr.emreone.adventofcode.year2017

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day08>(false) {
        Resources.resourceAsList("year2017/day08_example.txt")
            .joinToString("\n") part1 -1 part2 -1
    }

}
