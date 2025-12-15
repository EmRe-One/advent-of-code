package tr.emreone.adventofcode.year2024

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.solve

fun main() {

    solve<Day17>(false) {
        Resources.resourceAsList("year2024/day17_example.txt")
            .joinToString("\n") part1 "5,7,3,0" part2 117_440
    }

}
