package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day
import kotlin.math.max

class Day15 : Day(
    15,
    2015,
    "Science for Hungry People",
    session = Resources.resourceAsString("session.cookie")
) {


    class Ingredient(
        val name: String,
        val capacity: Long,
        val durability: Long,
        val flavor: Long,
        val texture: Long,
        val calories: Long
    ) {
        companion object {
            fun of(line: String): Ingredient {
                val regex =
                    """^(?<name>\w+): capacity (?<capacity>-?\d+), durability (?<durability>-?\d+), flavor (?<flavor>-?\d+), texture (?<texture>-?\d+), calories (?<calories>-?\d+)$""".toRegex()
                val matchResult = regex.find(line) ?: throw IllegalArgumentException("Invalid ingredient line: $line")
                val (name, capacity, durability, flavor, texture, calories) = matchResult.destructured

                return Ingredient(
                    name,
                    capacity.toLong(),
                    durability.toLong(),
                    flavor.toLong(),
                    texture.toLong(),
                    calories.toLong()
                )
            }
        }
    }

    override fun part1(): Long {
        val ingredients = inputAsList.map { Ingredient.of(it) }

        val results = mutableListOf<Long>()

        if (ingredients.size == 2) {
            (0..100).forEach { a ->
                val b = 100 - a
                if (b < 0) {
                    return@forEach
                }
                val ingA = ingredients[0]
                val ingB = ingredients[1]
                val score = (max(0L, ingA.capacity * a + ingB.capacity * b))
                    .times(max(0L, ingA.durability * a + ingB.durability * b))
                    .times(max(0L, ingA.flavor * a + ingB.flavor * b))
                    .times(max(0L, ingA.texture * a + ingB.texture * b))

                results.add(max(0L, score))
            }
        } else if (ingredients.size == 4) {
            (0..100).forEach { a ->
                (0..100).forEach { b ->
                    (0..100).forEach { c ->
                        val d = 100 - a - b - c
                        if (d < 0) {
                            return@forEach
                        }
                        val ingA = ingredients[0]
                        val ingB = ingredients[1]
                        val ingC = ingredients[2]
                        val ingD = ingredients[3]
                        val score = (max(0L, ingA.capacity * a + ingB.capacity * b + ingC.capacity * c + ingD.capacity * d))
                            .times(max(0L, ingA.durability * a + ingB.durability * b + ingC.durability * c + ingD.durability * d))
                            .times(max(0L, ingA.flavor * a + ingB.flavor * b + ingC.flavor * c + ingD.flavor * d))
                            .times(max(0L, ingA.texture * a + ingB.texture * b + ingC.texture * c + ingD.texture * d))
                        results.add(max(0L, score))

                    }
                }
            }
        } else {
            throw IllegalArgumentException("Unsupported number of ingredients: ${ingredients.size}")
        }

        return results.max()
    }

    override fun part2(): Long {
        val ingredients = inputAsList.map { Ingredient.of(it) }

        val results = mutableListOf<Long>()

        if (ingredients.size == 2) {
            (0..100).forEach { a ->
                val b = 100 - a
                if (b < 0) {
                    return@forEach
                }
                val ingA = ingredients[0]
                val ingB = ingredients[1]
                val calories = ingA.calories * a + ingB.calories * b
                if (calories != 500L) {
                    return@forEach
                }

                val score = (max(0L, ingA.capacity * a + ingB.capacity * b))
                    .times(max(0L, ingA.durability * a + ingB.durability * b))
                    .times(max(0L, ingA.flavor * a + ingB.flavor * b))
                    .times(max(0L, ingA.texture * a + ingB.texture * b))

                results.add(max(0L, score))
            }
        } else if (ingredients.size == 4) {
            (0..100).forEach { a ->
                (0..100).forEach { b ->
                    (0..100).forEach { c ->
                        val d = 100 - a - b - c
                        if (d < 0) {
                            return@forEach
                        }
                        val ingA = ingredients[0]
                        val ingB = ingredients[1]
                        val ingC = ingredients[2]
                        val ingD = ingredients[3]

                        val calories = ingA.calories * a + ingB.calories * b + ingC.calories * c + ingD.calories * d

                        if (calories != 500L) {
                            return@forEach
                        }

                        val score = (max(0L, ingA.capacity * a + ingB.capacity * b + ingC.capacity * c + ingD.capacity * d))
                            .times(max(0L, ingA.durability * a + ingB.durability * b + ingC.durability * c + ingD.durability * d))
                            .times(max(0L, ingA.flavor * a + ingB.flavor * b + ingC.flavor * c + ingD.flavor * d))
                            .times(max(0L, ingA.texture * a + ingB.texture * b + ingC.texture * c + ingD.texture * d))
                        results.add(max(0L, score))

                    }
                }
            }
        } else {
            throw IllegalArgumentException("Unsupported number of ingredients: ${ingredients.size}")
        }

        return results.max()
    }

}
