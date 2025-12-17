package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day
import java.util.Comparator
import java.util.PriorityQueue
import kotlin.collections.isNotEmpty
import kotlin.collections.map
import kotlin.collections.plusAssign

class Day19 : Day(
    19,
    2015,
    "Medicine for Rudolph",
    session = Resources.resourceAsString("session.cookie")
) {

    fun replace(input: String, rules: List<Pair<String, String>>): Sequence<String> = sequence {
        for (i in input.indices) {
            for (rule in rules) {
                if (rule.first.length <= i + 1) {
                    var matches = true
                    for (j in rule.first.indices) {
                        if (input[i + 1 - rule.first.length + j] != rule.first[j]) {
                            matches = false
                            break
                        }
                    }
                    if (matches) {
                        val builder = StringBuilder(input.length + rule.second.length - rule.first.length)
                        for (k in 0 until i + 1 - rule.first.length) {
                            builder.append(input[k])
                        }
                        builder.append(rule.second)
                        for (k in i + 1 until input.length) {
                            builder.append(input[k])
                        }
                        yield(builder.toString())
                    }
                }
            }
        }
    }

    fun factory(target: String, start: String, rules: List<Pair<String, String>>): Int {
        val queue = PriorityQueue<Pair<String, Int>>(Comparator.comparing { it.first.length })
        queue += Pair(start, 0)
        while (queue.isNotEmpty()) {
            val current = queue.poll()

            if (current.first == target) {
                return current.second
            }

            for (next in replace(current.first, rules)) {
                queue += Pair(next, current.second + 1)
            }
        }
        return 0
    }

    val rules = mutableListOf<Pair<String, String>>()
    lateinit var molecule: String

    override fun part1(): Int {
        inputAsString.also { text ->
            val parts = text.trim().split("\n\n")
            require(parts.size == 2)
            for (line in parts[0].split("\n")) {
                val components = line.trim().split(" => ")
                require(components.size == 2)
                rules += Pair(components[0], components[1])
            }
            molecule = parts[1].trim()
        }

        val result1 = replace(molecule, rules)
        return result1.toSet().size
    }

    override fun part2(): Int {
        assert(::molecule.isInitialized)
        val invertedRules = rules.map { Pair(it.second, it.first) }

        return factory("e", molecule, invertedRules)
    }

}
