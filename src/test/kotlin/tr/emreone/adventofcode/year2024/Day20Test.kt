package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day20>(false) {
        Resources.resourceAsList("year2024/day20_example.txt")
            .joinToString("\n") part1 10 part2 41
    }

}
