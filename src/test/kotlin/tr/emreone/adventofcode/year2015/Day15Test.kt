package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day15>(false) {
        Resources.resourceAsList("year2015/day15_example.txt")
            .joinToString("\n") part1 62_842_880L part2 57_600_000L
    }

}
