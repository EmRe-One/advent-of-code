package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day
import tr.emreone.kotlin_utils.math.Direction4
import tr.emreone.kotlin_utils.math.Point
import tr.emreone.kotlin_utils.math.Point2D
import tr.emreone.kotlin_utils.math.plus
import tr.emreone.kotlin_utils.math.x
import tr.emreone.kotlin_utils.math.y
import kotlin.text.iterator

class Day03 : Day(
    3,
    2015,
    "Perfectly Spherical Houses in a Vacuum",
    session = Resources.resourceAsString("session.cookie")
) {

    override fun part1(): List<Int> {
        return inputAsList.map { line ->
            val housesCoords: MutableSet<Point> = mutableSetOf()
            var currentHouse = Point(0, 0)

            housesCoords.add(currentHouse)
            for (direction in line) {
                currentHouse = when (direction) {
                    '^' -> currentHouse + Direction4.NORTH
                    '>' -> currentHouse + Direction4.EAST
                    'v' -> currentHouse + Direction4.SOUTH
                    '<' -> currentHouse + Direction4.WEST
                    else -> throw IllegalArgumentException("Unknown direction: $direction")
                }
                housesCoords.add(currentHouse)
            }
            housesCoords.size
        }
    }

    override fun part2(): List<Int> {
        return inputAsList.map { line ->
            val housesCoords: MutableSet<Point> = mutableSetOf()
            var currentSantaHouse = Point(0, 0)
            var currentRoboSantaHouse = Point(0, 0)

            housesCoords.add(currentSantaHouse)
            line.forEachIndexed { index, direction ->
                if (index % 2 == 0) {
                    currentSantaHouse = when (direction) {
                        '^' -> currentSantaHouse + Direction4.NORTH
                        '>' -> currentSantaHouse + Direction4.EAST
                        'v' -> currentSantaHouse + Direction4.SOUTH
                        '<' -> currentSantaHouse + Direction4.WEST
                        else -> throw IllegalArgumentException("Unknown direction: $direction")
                    }
                    housesCoords.add(currentSantaHouse)
                }
                else {
                    currentRoboSantaHouse = when (direction) {
                        '^' -> currentRoboSantaHouse + Direction4.NORTH
                        '>' -> currentRoboSantaHouse + Direction4.EAST
                        'v' -> currentRoboSantaHouse + Direction4.SOUTH
                        '<' -> currentRoboSantaHouse + Direction4.WEST
                        else -> throw IllegalArgumentException("Unknown direction: $direction")
                    }
                    housesCoords.add(currentRoboSantaHouse)
                }
            }
            housesCoords.size
        }
    }

}
