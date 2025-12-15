package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day10 : Day(
    10,
    2015,
    "Elves Look, Elves Say",
    session = Resources.resourceAsString("session.cookie")
) {

    private fun solve(input: String, repeat: Int): Int {
        var currentString = input

        repeat(repeat) {
            currentString = buildString {
                val length = currentString.length
                var index = 0

                while(index < length) {
                    val currentDigit = currentString[index]
                    var counter = 1
                    index++

                    while(index < length && currentDigit == currentString[index]) {
                        counter++
                        index++
                    }

                    append("$counter$currentDigit")
                }
            }
        }

        return currentString.length
    }

    override fun part1(): Int {
        return solve(inputAsString, 40)
    }

    override fun part2(): Int {
        return solve(inputAsString, 50)
    }

}
