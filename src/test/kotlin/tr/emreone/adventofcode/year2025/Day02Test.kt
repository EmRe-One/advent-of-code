package tr.emreone.adventofcode.year2025

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day02>(false) {
        Resources.resourceAsList("year2025/day02_example.txt")
            .joinToString("\n") part1 1_227_775_554L part2 4_174_379_265L
    }

}
