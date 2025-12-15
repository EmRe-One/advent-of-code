package tr.emreone.adventofcode24.days

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day11>(false) {
        Resources.resourceAsList("day11_example.txt")
            .joinToString("\n") part1 55_312 part2 65_601_038_650_482L
    }

}
