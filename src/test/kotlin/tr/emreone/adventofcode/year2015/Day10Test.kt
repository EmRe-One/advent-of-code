package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day10>(false) {
        Resources.resourceAsList("year2015/day10_example.txt")
            .joinToString("\n") part1 82_350 part2 1_166_642
    }

}
