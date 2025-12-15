package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day08 : Day(
    8,
    2015,
    "Matchsticks",
    session = Resources.resourceAsString("session.cookie")
) {

    override fun part1(): Int {
        val regexHex = """(\\x[0-9a-fA-F]{2})""".toRegex()
        val regexEscaped = """(\\\\|\\")""".toRegex()

        return inputAsList.sumOf {
            val trimmed = it.substring(1, it.length - 1)
            val numberOfHex = regexHex.findAll(trimmed).count()
            val numberOfEscaped = regexEscaped.findAll(trimmed).count()

            // \x00     -> 4 chars in line -> equals to one char in memory
            // \\ or \" -> 2 chars in line -> equals to one char in memory
            val stringLiterals = it.length
            val numberOfCharsInString = trimmed.length - numberOfHex * 3 - numberOfEscaped

            stringLiterals - numberOfCharsInString
        }
    }

    override fun part2(): Int {
        val regexHex = """(\\x[0-9a-fA-F]{2})""".toRegex()
        val regexEscaped = """(\\\\|\\")""".toRegex()

        return inputAsList.sumOf {
            val trimmed = it.substring(1, it.length - 1)

            val numberOfHex = regexHex.findAll(trimmed).count()
            val numberOfEscaped = regexEscaped.findAll(trimmed).count()

            // \x00     -> 4 chars in line -> adds a \ to the front             -->  \\x00
            // \\ or \" -> 2 chars in line -> adds two \ to the line            --> \\\\ or \\\"
            // "..."    -> 2 chars in line -> adds 4 chars to the encoded line  --> "\"...\""
            val numberOfEncodedLine = trimmed.length + numberOfHex + numberOfEscaped * 2 + 6
            val stringLiterals = it.length

            numberOfEncodedLine - stringLiterals
        }
    }

}
