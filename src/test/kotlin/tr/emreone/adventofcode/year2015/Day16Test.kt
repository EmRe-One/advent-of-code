package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day16>(false) {
        Resources.resourceAsList("year2015/day16_example.txt")
            .joinToString("\n") part1 -1 part2 -1
    }

}
