package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

class Day11 : Day(
    11,
    2015,
    "Corporate Policy",
    session = Resources.resourceAsString("session.cookie")
) {

    class Password(var password: String) {
        fun incrementPassword() {
            val length = password.length
            var index = length - 1

            while (index >= 0) {
                val c = password[index]
                if (c == 'z') {
                    password = password.substring(0, index) + 'a' + password.substring(index + 1)
                } else {
                    password = password.substring(0, index) + (c + 1) + password.substring(index + 1)
                    break
                }
                index--
            }
        }

        private fun isValid(): Boolean {
            // check first:
            // Passwords may not contain the letters i, o, or l,
            // as these letters can be mistaken for other characters and are therefore confusing.
            if (this.password.contains("""(i|o|l)""".toRegex())) {
                return false
            }

            // check second:
            // Passwords must contain at least two different, non-overlapping pairs of letters,
            // like aa, bb, or zz.
            val pairs = this.password
                .windowed(2) { "${it[0]}${it[1]}" }
                .filter { it[0] == it[1] }
                .groupBy { it }
            if (pairs.size < 2) {
                return false
            }

            // check third:
            // Passwords must include one increasing straight of at least three letters,
            // like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; abd doesn't count.
            val triple = this.password
                .windowed(3)
                .count {
                    (it[0] <= 'x') && (it[0] + 1 == it[1]) && (it[1] + 1 == it[2])
                }

            return triple != 0
        }

        fun findNextValidPassword() {
            while (!isValid()) {
                incrementPassword()
            }
        }
    }

    override fun part1(): String {
        val pw = Password(inputAsString)
        pw.findNextValidPassword()
        return pw.password
    }

    override fun part2(): String {
        val pw = Password(inputAsString)
        pw.findNextValidPassword()
        pw.incrementPassword()
        pw.findNextValidPassword()
        return pw.password
    }

}
