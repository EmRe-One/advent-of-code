package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day

@OptIn(ExperimentalUnsignedTypes::class)
class Day07 : Day(
    7,
    2015,
    "Some Assembly Required",
    session = Resources.resourceAsString("session.cookie")
) {

    class Instruction (val variable: String, val operation: (vars: UShortArray) -> UShort, vararg val parameter: String) {
        var value: UShort? = null

        fun execute(vars: UShortArray): UShort {
            if (value == null) {
                value = operation(vars)
            }
            return value!!
        }
    }

    private fun parseInstruction(instruction: String): Instruction {
        if (instruction.startsWith("NOT")) {
            val regex = """NOT (\w+) -> (\w+)""".toRegex()
            val (var1, variable) = regex.matchEntire(instruction)!!.destructured

            return Instruction(variable, {
                it[0].inv()
            }, var1)
        }
        else {
            val regex0 = """(\d+) -> (\w+)""".toRegex()
            val regex1 = """(\w+) (AND|OR|LSHIFT|RSHIFT) (\w+) -> (\w+)""".toRegex()
            val regex2 = """(\w+) -> (\w+)""".toRegex()

            if (regex0.matches(instruction)) {
                val (value, variable) = regex0.matchEntire(instruction)!!.destructured

                return Instruction(variable, {
                    it[0]
                }, value)
            }
            else if (regex1.matches(instruction)) {
                val (var1, operation, var2, variable) = regex1.find(instruction)!!.destructured

                return Instruction(variable, {
                    when (operation) {
                        "AND"    -> it[0] and it[1]
                        "OR"     -> it[0] or it[1]
                        "LSHIFT" -> (it[0].toInt() shl it[1].toInt()).toUShort()
                        "RSHIFT" -> (it[0].toInt() shr it[1].toInt()).toUShort()
                        else     -> throw IllegalArgumentException("Unknown operation: $operation")
                    }
                }, var1, var2)
            }
            else if (regex2.matches(instruction)){
                val (var1, variable) = regex2.find(instruction)!!.destructured

                return Instruction(variable, {
                    it[0]
                }, var1)
            }
            else {
                throw IllegalArgumentException("Unknown instruction: $instruction")
            }
        }
    }

    fun parseVariables(input: List<String>): Map<String, UShort> {
        val map = mutableMapOf<String, UShort>()

        val instructions = input.map { line ->
            parseInstruction(line)
        }

        while (instructions.any { it.value == null }) {
            instructions.forEach { inst ->
                if (inst.value == null) {
                    val parameter = inst.parameter.map {
                        if (it[0].isDigit()) {
                            it.toUShort()
                        }
                        else {
                            val nullableValue = map[it]
                            nullableValue
                        }
                    }

                    if (! parameter.any { it == null }) {
                        map[inst.variable] = inst.execute(parameter.map { it!! }.toUShortArray())
                    }
                }
            }
        }

        return map
    }

    override fun part1(): UShort {
        val map = parseVariables(inputAsList)

        return map["a"] ?: 0.toUShort()
    }

    override fun part2(): UShort {
        val map = parseVariables(inputAsList)
        val valueForA = map["a"] ?: 0
        val index = inputAsList.indexOfLast { it.contains("-> b") }

        val mutableList = inputAsList.toMutableList()
        mutableList[index] = "$valueForA -> b"
        val map2 = parseVariables(mutableList)

        return map2["a"] ?: 0.toUShort()
    }

}
