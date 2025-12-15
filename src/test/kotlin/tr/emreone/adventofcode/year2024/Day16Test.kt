package tr.emreone.adventofcode24.days

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day16>(false) {
        Resources.resourceAsList("day16_example.txt")
            .joinToString("\n") part1 11_048 part2 64
    }

}
