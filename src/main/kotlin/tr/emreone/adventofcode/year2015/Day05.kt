package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day05 : Day(
    5,
    2015,
    "Doesn't He Have Intern-Elves For This?",
    session = Resources.resourceAsString("session.cookie")
) {

    override fun part1(): Int {
        return inputAsList.count { line ->
            val condition1 = line.count {
                arrayOf('a', 'e', 'i', 'o', 'u').contains(it)
            } >= 3
            val condition2 = line.windowed(2).count {
                it[0] == it[1]
            } > 0
            val condition3 = line.windowed(2).count {
                arrayOf("ab", "cd", "pq", "xy").contains(it)
            } > 0

            condition1 && condition2 && !condition3
        }
    }

    override fun part2(): Int {
        return inputAsList.count { line ->
            val condition1 = line.windowed(2).count {
                val regex = """($it)([a-zA-Z]*)($it)""".toRegex()
                val match = regex.find(line)

                match != null
            } > 0
            val condition2 = line.windowed(3).count {
                it[0] == it[2]
            } > 0

            condition1 && condition2
        }
    }

}
