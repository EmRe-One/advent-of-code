package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day16 : Day(
    16,
    2015,
    "Aunt Sue",
    session = Resources.resourceAsString("session.cookie")
) {

    data class Sue(val id: Int, val props: Map<String, Int>) {
        companion object {
            operator fun invoke(string: String): Sue {
                val components = string.split(" ", ":", ",")
                    .toList()
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }

                val id = components[1].toInt()
                val props = components.subList(2, components.size)
                    .chunked(2)
                    .associate {
                        it[0] to it[1].toInt()
                    }

                return Sue(id, props)
            }
        }
    }

    private val knownPropsString = """
        children: 3
        cats: 7
        samoyeds: 2
        pomeranians: 3
        akitas: 0
        vizslas: 0
        goldfish: 5
        trees: 3
        cars: 2
        perfumes: 1
    """.trimIndent()

    val knownProps: Map<String, Int> = knownPropsString
        .split("\n")
        .associate { line ->
            val (name, value) = line.split(":")
            name to value.trim().toInt()
        }

    val sues = inputAsList.map { line ->
        Sue(line)
    }

    override fun part1(): Int {
        return sues.firstOrNull { sue ->
            for (entry in sue.props) {
                val known = knownProps[entry.key] ?: continue
                if (known != entry.value) return@firstOrNull false
            }
            return@firstOrNull true
        }?.id ?: -1
    }

    override fun part2(): Int {
        return sues.firstOrNull { sue ->
            for (entry in sue.props) {
                val known = knownProps[entry.key] ?: continue
                when (entry.key) {
                    "cats", "trees" -> if (entry.value <= known) return@firstOrNull false
                    "pomeranians", "goldfish" -> if (entry.value >= known) return@firstOrNull false
                    else -> if (entry.value != known) return@firstOrNull false
                }
            }
            return@firstOrNull true
        }?.id ?: -1
    }

}
