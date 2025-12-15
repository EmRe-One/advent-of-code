package tr.emreone.adventofcode.year2022

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day02>(false) {
        Resources.resourceAsList("year2022/day02_example.txt")
            .joinToString("\n") part1 -1 part2 -1
    }

}
