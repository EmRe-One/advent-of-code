package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day20 : Day(
    20,
    2015,
    "Infinite Elves and Infinite Houses",
    session = Resources.resourceAsString("session.cookie")
) {

    private fun getFactors(n: Int): List<Int> {
        var i = 1
        val result = mutableListOf<Int>()
        while (i * i <= n) {
            if (n % i == 0) {
                val d = n / i
                result += d
                if (d != i) result += i
            }
            i++
        }
        return result
    }

    private fun getTarget(number: Int, divisor: Int): Int {
        return if (number % divisor == 0) {
            number / divisor
        } else {
            (number / divisor) + 1
        }
    }

    override fun part1(): Int {
        val number = inputAsString.trim().toInt()
        val target = getTarget(number, 10)
        var houseNumber = 1
        while (true) {
            val total = getFactors(houseNumber).sum()
            if (total >= target) break
            houseNumber++
        }
        return houseNumber
    }

    override fun part2(): Int {
        val number = inputAsString.trim().toInt()
        val target = getTarget(number, 11)
        val seenCount = IntArray(target + 1)
        var houseNumber = 1
        while (true) {
            val total = getFactors(houseNumber).sumOf {
                if (seenCount[it]++ < 50) it else 0
            }
            if (total >= target) break
            houseNumber++
        }
        return houseNumber
    }

}
