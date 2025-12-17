package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day
import tr.emreone.kotlin_utils.extensions.Grid
import tr.emreone.kotlin_utils.extensions.height
import tr.emreone.kotlin_utils.extensions.width
import tr.emreone.kotlin_utils.math.Point
import tr.emreone.kotlin_utils.math.surroundingNeighbors
import tr.emreone.kotlin_utils.math.x
import tr.emreone.kotlin_utils.math.y

class Day18 : Day(
    18,
    2015,
    "Like a GIF For Your Yard",
    session = Resources.resourceAsString("session.cookie")
) {

    fun Grid<Char>.isExtreme(point: Point): Boolean {
        return when (point) {
            Point(0, 0),
            Point(0, this.height - 1),
            Point(this.width - 1, 0),
            Point(this.width - 1, this.height - 1) -> true

            else -> false
        }
    }

    private fun simulate(input: Grid<Char>, part2: Boolean = false): Grid<Char> {
        return Grid(input.width, input.height) { (x, y) ->
            val coordinate = Point(x, y)
            val neighbourOnCount = coordinate
                .surroundingNeighbors()
                .filter { it.y in input.indices && it.x in input[0].indices }
                .count {
                    input[it.y][it.x] == '#' || (part2 && input.isExtreme(it))
                }
            val on = if (part2 && input.isExtreme(coordinate)) {
                true
            } else if (input[coordinate.y][coordinate.x] == '#') {
                neighbourOnCount in 2..3
            } else {
                neighbourOnCount == 3
            }
            if (on) '#' else '.'
        }
    }

    override fun part1(): Int {
        var map = inputAsGrid
        val n = if (inputAsGrid.height < 10) 4 else 100
        // TODO: create a gif here for visualization
        //  val gifFrames = mutableListOf<Grid<Char>>()

        repeat(n) {
            map = simulate(map)
            // gifFrames.add(map)
        }

        return map.sumOf { row -> row.count { cell -> cell == '#' } }
    }

    override fun part2(): Int {
        var map = inputAsGrid
        val n = if (inputAsGrid.height < 10) 4 else 100
        repeat(n) {
            map = simulate(map, true)
        }

        return map.sumOf { row -> row.count { cell -> cell == '#' } }
    }

}
