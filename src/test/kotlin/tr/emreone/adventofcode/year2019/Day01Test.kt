package tr.emreone.adventofcode.year2019

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day01>(false) {
        Resources.resourceAsList("year2019/day01_example.txt")
            .joinToString("\n") part1 -1 part2 -1
    }

}
