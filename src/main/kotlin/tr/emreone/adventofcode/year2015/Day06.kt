package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day
import kotlin.math.max

class Day06 : Day(
    6,
    2015,
    "Probably a Fire Hazard",
    session = Resources.resourceAsString("session.cookie")
) {

    enum class Command {
        ON,
        OFF,
        TOGGLE
    }

    override fun part1(): Int {
        val regex = """^(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)$""".toRegex()
        val lightsGrid = Array(1000) { IntArray(1000) { 0 } }

        inputAsList.forEach {
            val (command, x1, y1, x2, y2) = regex.matchEntire(it)!!.destructured
            val commandValue = when (command) {
                "turn on"  -> Command.ON
                "turn off" -> Command.OFF
                "toggle"   -> Command.TOGGLE
                else -> throw IllegalArgumentException("Unknown command: $command")
            }

            for (x in x1.toInt()..x2.toInt()) {
                for (y in y1.toInt()..y2.toInt()) {
                    when(commandValue) {
                        Command.ON     -> lightsGrid[x][y] = 1
                        Command.OFF    -> lightsGrid[x][y] = 0
                        Command.TOGGLE -> lightsGrid[x][y] = (lightsGrid[x][y] + 1) % 2
                    }
                }
            }
        }

        return lightsGrid.sumOf { line ->
            line.sum()
        }
    }

    override fun part2(): Int {
        val regex = """^(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)$""".toRegex()
        val lightsGrid = Array(1000) { IntArray(1000) { 0 } }

        inputAsList.forEach {
            val (command, x1, y1, x2, y2) = regex.matchEntire(it)!!.destructured
            val commandValue = when (command) {
                "turn on"  -> +1
                "turn off" -> -1
                "toggle"   -> +2
                else -> throw IllegalArgumentException("Unknown command: $command")
            }

            for (x in x1.toInt()..x2.toInt()) {
                for (y in y1.toInt()..y2.toInt()) {
                    lightsGrid[x][y] = max(0, lightsGrid[x][y] + commandValue)
                }
            }
        }

        return lightsGrid.sumOf { line ->
            line.sum()
        }
    }

}
