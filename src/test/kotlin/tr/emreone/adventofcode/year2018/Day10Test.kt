package tr.emreone.adventofcode.year2018

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day10>(false) {
        Resources.resourceAsList("year2018/day10_example.txt")
            .joinToString("\n") part1 -1 part2 -1
    }

}
