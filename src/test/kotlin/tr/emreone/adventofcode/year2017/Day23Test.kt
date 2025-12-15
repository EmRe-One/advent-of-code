package tr.emreone.adventofcode.year2017

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day23>(false) {
        Resources.resourceAsList("year2017/day23_example.txt")
            .joinToString("\n") part1 -1 part2 -1
    }

}
