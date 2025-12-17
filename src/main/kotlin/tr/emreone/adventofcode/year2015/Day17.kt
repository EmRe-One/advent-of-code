package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day17 : Day(
    17,
    2015,
    "No Such Thing as Too Much",
    session = Resources.resourceAsString("session.cookie")
) {

    fun findCombinations(
        remainingContainers: List<Int>,
        target: Int,
        currentCombination: List<Int>,
        startIndex: Int,
        allResults: MutableList<List<Int>>
    ) {
        val currentSum = currentCombination.sum()

        if (currentSum == target) {
            allResults.add(currentCombination)
            return
        }

        if (currentSum > target || startIndex >= remainingContainers.size) {
            return
        }

        for (i in startIndex until remainingContainers.size) {
            val nextContainer = remainingContainers[i]

            findCombinations(
                remainingContainers,
                target,
                currentCombination + nextContainer, // Neuer Pfad
                i + 1,
                allResults
            )
        }
    }

    lateinit var possibleContainers: List<List<Int>>

    override fun part1(): Int {
        val containers = inputAsList
            .map { line ->
                line.toInt()
            }
            .sortedDescending()
        val target = if (containers.size < 10) 25 else 150
        val results = mutableListOf<List<Int>>()

        findCombinations(
            remainingContainers = containers,
            target = target,
            currentCombination = listOf(),
            startIndex = 0,
            allResults = results
        )

        possibleContainers = results.toList()

        return possibleContainers.size
    }

    override fun part2(): Int {
        assert(::possibleContainers.isInitialized)

        val minCount = possibleContainers.minOfOrNull { it.size } ?: 0

        return possibleContainers.count { it.size == minCount }
    }

}
