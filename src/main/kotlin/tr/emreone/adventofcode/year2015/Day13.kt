package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Logger.logger
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day
import tr.emreone.kotlin_utils.extensions.permutations

class Day13 : Day(
    13,
    2015,
    "Knights of the Dinner Table",
    session = Resources.resourceAsString("session.cookie")
) {

    class Person(val name: String) {
        private val happinessMatrix = mutableMapOf<String, Int>()

        fun addPossibleNeighbor(other: String, happiness: Int) {
            happinessMatrix[other] = happiness
        }

        fun getHappinessTo(other: String): Int {
            return happinessMatrix[other] ?: 0
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Person

            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            return name.hashCode()
        }
    }

    class CircularTable(input: List<String>) {
        /*
        David would lose 7 happiness units by sitting next to Bob.
        David would gain 41 happiness units by sitting next to Carol.
         */
        val persons = mutableMapOf<String, Person>()

        init {
            val pattern = """^(\w+) would (gain|lose) (\d+) happiness units by sitting next to (\w+).$""".toRegex()

            input.forEach { line ->
                val (name, type, happiness, neighbor) = pattern.matchEntire(line)?.destructured!!

                val p = persons.getOrPut(name) { Person(name) }
                val h = if (type == "gain") happiness.toInt() else -happiness.toInt()

                p.addPossibleNeighbor(neighbor, h)
            }
        }

        fun addMe() {
            val me = Person("Me")
            this.persons.values.forEach { person ->
                person.addPossibleNeighbor("Me", 0)
                me.addPossibleNeighbor(person.name, 0)
            }
            this.persons["Me"] = me
        }

        fun calcHappinessOfSeating(seating: List<Pair<String, Person>>): Int {
            var happinessSum = 0

            // beacause of the circularity, we need to add the first person to the end
            // 1 - 2 - 3 - ... - n - 1
            val modifiedSeating = seating + seating.first()

            modifiedSeating.windowed(2).forEach { (p1, p2) ->
                happinessSum += p1.second.getHappinessTo(p2.first) + p2.second.getHappinessTo(p1.first)
            }

            logger.debug {
                "Seating arrangement: ${
                    modifiedSeating.map { it.first }.joinToString(", ")
                }, happiness: $happinessSum"
            }

            return happinessSum
        }

        fun findOptimalSeatingArrangement(): List<Pair<String, Person>> {
            val optimalSeating = this.persons.toList()
                .permutations()
                .maxByOrNull {
                    calcHappinessOfSeating(it)
                }

            return optimalSeating ?: emptyList()
        }
    }

    override fun part1(): Int {
        val table = CircularTable(inputAsList)
        val optimalSeating = table.findOptimalSeatingArrangement()
        return table.calcHappinessOfSeating(optimalSeating)
    }

    override fun part2(): Int {
        val table = CircularTable(inputAsList)
        table.addMe()

        val optimalSeating = table.findOptimalSeatingArrangement()
        return table.calcHappinessOfSeating(optimalSeating)
    }

}
