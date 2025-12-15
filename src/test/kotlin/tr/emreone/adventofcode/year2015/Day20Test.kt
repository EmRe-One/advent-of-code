package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day20>(false) {
        Resources.resourceAsList("year2015/day20_example.txt")
            .joinToString("\n") part1 -1 part2 -1
    }

}
