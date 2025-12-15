package tr.emreone.adventofcode24.days

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day07>(false) {
        Resources.resourceAsList("day07_example.txt")
            .joinToString("\n") part1 3749 part2 11387
    }

}
