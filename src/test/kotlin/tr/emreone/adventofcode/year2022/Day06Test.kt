package tr.emreone.adventofcode.year2022

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day06>(false) {
        Resources.resourceAsList("year2022/day06_example.txt")
            .joinToString("\n") part1 41 part2 6
    }

}
