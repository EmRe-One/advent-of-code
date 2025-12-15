package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day01 : Day(
    1,
    2015,
    "Not Quite Lisp",
    session = Resources.resourceAsString("session.cookie")
) {

    override fun part1(): List<Int> {
        return inputAsList.map { line ->
            val groups = line.groupBy { it }

            (groups['(']?.size ?: 0) - (groups[')']?.size ?: 0)
        }
    }

    override fun part2(): Int {
        var position = 0
        var floor = 0

        while(position < inputAsString.length) {
            if (inputAsString[position] == '(') {
                floor++
            }
            else {
                floor--
            }

            if (floor == -1) {
                return position + 1
            }
            else {
                position++
            }
        }

        return -1
    }

}
