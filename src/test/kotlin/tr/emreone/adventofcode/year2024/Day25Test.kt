package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day25>(false) {
        Resources.resourceAsList("year2024/day25_example.txt")
            .joinToString("\n") part1 3
    }

}
