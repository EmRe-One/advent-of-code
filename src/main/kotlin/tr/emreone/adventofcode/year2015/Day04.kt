package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day
import tr.emreone.kotlin_utils.extensions.md5

class Day04 : Day(
    4,
    2015,
    "The Ideal Stocking Stuffer ",
    session = Resources.resourceAsString("session.cookie")
) {


    private fun md5(input: String): String {
        val hexString = input.md5()
        return hexString.padStart(32, '0')
    }

    override fun part1(): List<Int> {
        return inputAsList.map { secretKey ->
            var number = 346385

            while(true){
                val hash = md5("$secretKey$number")

                if( hash.startsWith("00000")){
                    break
                }
                number++
            }

            number
        }
    }

    override fun part2(): List<Int> {
        return inputAsList.map { secretKey ->
            var number = 0

            while(true){
                val hash = md5("$secretKey$number")

                if( hash.startsWith("000000")){
                    break
                }
                number++
            }

            number
        }
    }

}
