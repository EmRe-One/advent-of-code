package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day02 : Day(
    2,
    2015,
    "I Was Told There Would Be No Math",
    session = Resources.resourceAsString("session.cookie")
) {

    override fun part1(): Int {
        val regex = """^(?<length>\d+)x(?<width>\d+)x(?<height>\d+)$""".toRegex()

        return inputAsList.sumOf { line ->
            val (l, w, h) = regex.find(line)?.destructured!!
            val side1 = l.toInt() * w.toInt()
            val side2 = w.toInt() * h.toInt()
            val side3 = h.toInt() * l.toInt()

            2 * side1 + 2 * side2 + 2 * side3 + minOf(side1, side2, side3)
        }
    }

    override fun part2(): Int {
        val regex = """^(?<length>\d+)x(?<width>\d+)x(?<height>\d+)$""".toRegex()

        return inputAsList.sumOf { line ->
            val (l, w, h) = regex.find(line)?.destructured!!
            val lengths = listOf(l.toInt(), w.toInt(), h.toInt()).sorted()

            val wrapRibbon = 2 * lengths[0] + 2 * lengths[1]
            val bowRibbon = lengths[0] * lengths[1] * lengths[2]

            wrapRibbon + bowRibbon
        }
    }

}
