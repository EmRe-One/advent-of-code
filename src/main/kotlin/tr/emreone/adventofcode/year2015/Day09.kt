package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Logger
import tr.emreone.kotlin_utils.Logger.logger
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day09 : Day(
    9,
    2015,
    "All in a Single Night",
    session = Resources.resourceAsString("session.cookie")
) {

    class Navigation(input: List<String>) {
        private val cities = mutableSetOf<String>()
        private val pattern = """^(\w+) to (\w+) = (\d+)$""".toRegex()
        private val distanceMatrix = input.map { line ->
            val (from, to, distance) = pattern.matchEntire(line)!!.destructured
            cities.add(from)
            cities.add(to)
            Pair(from, to) to distance.toInt()
        }

        fun getDistanceBetween(from: String, to: String): Int {
            return distanceMatrix.first {
                it.first == Pair(from, to) || it.first == Pair(to, from)
            }.second
        }

        fun getShortestDistance(): Pair<Set<String>, Int> {
            return cities.map {
                shortestDistance(it)
            }.minByOrNull { it.second } ?: throw IllegalStateException("No path found")
        }

        fun getLongestDistance(): Pair<Set<String>, Int> {
            return cities.map {
                longestDistance(it)
            }.maxByOrNull { it.second } ?: throw IllegalStateException("No path found")
        }

        private fun shortestDistance(current: String, visited: Set<String> = setOf(current)): Pair<Set<String>, Int> {
            if (visited.size == cities.size) {
                // all cities reached, no more travel possible
                return visited to 0
            }
            var minDistance = Int.MAX_VALUE
            var minDistancePath: Set<String> = setOf()

            for (next in cities.filter { it != current }) {
                if (visited.contains(next)) {
                    continue
                }
                val nextDistance = shortestDistance(next, visited + next)
                val distance = getDistanceBetween(current, next) + nextDistance.second

                if (distance < minDistance) {
                    minDistance = distance
                    minDistancePath = nextDistance.first
                }
            }

            return minDistancePath to minDistance
        }

        private fun longestDistance(current: String, visited: Set<String> = setOf(current)): Pair<Set<String>, Int> {
            if (visited.size == cities.size) {
                // all cities reached, no more travel possible
                return visited to 0
            }
            var maxDistance = 0
            var maxDistancePath: Set<String> = setOf()

            for (next in cities.filter { it != current }) {
                if (visited.contains(next)) {
                    continue
                }
                val nextDistance = longestDistance(next, visited + next)
                val distance = getDistanceBetween(current, next) + nextDistance.second

                if (distance > maxDistance) {
                    maxDistance = distance
                    maxDistancePath = nextDistance.first
                }
            }

            return maxDistancePath to maxDistance
        }
    }

    override fun part1(): Int {
        val navigation = Navigation(inputAsList)
        val shortestDistance = navigation.getShortestDistance()
        val distanceString = buildString {
            shortestDistance.first.windowed(2).forEach { (from, to) ->
                append(from)
                append(" -(")
                append(navigation.getDistanceBetween(from, to))
                append(")-> ")
            }
            append(shortestDistance.first.last())
        }

        logger.debug { "Shortest distance: ${shortestDistance.second}" }
        logger.debug { distanceString }

        return shortestDistance.second
    }

    override fun part2(): Int {
        val navigation = Navigation(inputAsList)
        val longestDistance = navigation.getLongestDistance()
        val distanceString = buildString {
            longestDistance.first.windowed(2).forEach { (from, to) ->
                append(from)
                append(" -(")
                append(navigation.getDistanceBetween(from, to))
                append(")-> ")
            }
            append(longestDistance.first.last())
        }

        logger.debug { "Longest distance: ${longestDistance.second}" }
        logger.debug { distanceString }

        return longestDistance.second
    }

}
