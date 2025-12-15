package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day14 : Day(
    14,
    2015,
    "Reindeer Olympics",
    session = Resources.resourceAsString("session.cookie")
) {

    class Reindeer(val name: String, val speed: Int, val flyTime: Int, val restTime: Int) {

        /**
         * |__fly__|______________rest______________||__fly__|______________rest______________|
         * |-----------------------seconds---------------------------|
         *                                           |-----diff------|
         *
         * numberOfCycles = seconds / (flyTime + restTime)
         * diff = seconds - numberOfCycles * (flyTime + restTime)
         *
         * totalFlyTime = numberOfCycles * flyTime + (diff < flyTime ? diff : flyTime)
         */
        fun totalFlyTimeAt(seconds: Int): Int {
            val timeOfOnePeriod = this.flyTime + this.restTime
            val numberOfCycles = seconds / timeOfOnePeriod
            val diff = seconds - numberOfCycles * timeOfOnePeriod

            return numberOfCycles * this.flyTime + (if (diff < this.flyTime) diff else this.flyTime)
        }

        companion object {
            fun parse(line: String): Reindeer {
                val pattern = """^(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.""".toRegex()

                val (n, s, f, r) = pattern.matchEntire(line)!!.destructured
                return Reindeer(n, s.toInt(), f.toInt(), r.toInt())
            }
        }
    }

    override fun part1(): Int {
        val seconds: Int = if (inputAsList.size == 2) 1000 else 2503
        val reindeers = inputAsList.map { Reindeer.parse(it) }

        return reindeers.maxOf { it.totalFlyTimeAt(seconds) * it.speed }
    }

    override fun part2(): Int {
        val seconds: Int = if (inputAsList.size == 2) 1000 else 2503
        val reindeers = inputAsList.map { Reindeer.parse(it) }
        val scoreMap = reindeers.associate { it.name to 0 }.toMutableMap()

        for (second in 1..seconds) {
            val distances = reindeers.associate { it.name to (it.totalFlyTimeAt(second) * it.speed) }
            val leadingDistance = distances.maxOf { it.value }

            distances.filter {
                it.value == leadingDistance
            }.forEach { (name, _) ->
                scoreMap[name] = scoreMap[name]!! + 1
            }
        }
        return scoreMap.maxOf { it.value }
    }

}
