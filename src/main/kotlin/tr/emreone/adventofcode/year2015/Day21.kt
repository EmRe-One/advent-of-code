package tr.emreone.adventofcode.year2015

import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day
import kotlin.math.max

class Day21 : Day(
    21,
    2015,
    "",
    session = Resources.resourceAsString("session.cookie")
) {

    data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)

    private val weapons = listOf(
        Item("Dagger", 8, 4, 0),
        Item("Shortsword", 10, 5, 0),
        Item("Warhammer", 25, 6, 0),
        Item("Longsword", 40, 7, 0),
        Item("Greataxe", 74, 8, 0),
    )

    private val armors = listOf(
        Item("None", 0, 0, 0),
        Item("Leather", 13, 0, 1),
        Item("Chainmail", 31, 0, 2),
        Item("Splintmail", 53, 0, 3),
        Item("Bandedmail", 75, 0, 4),
        Item("Platemail", 102, 0, 5),
    )

    private val rings = listOf(
        Item("None", 0, 0, 0),
        Item("None", 0, 0, 0),
        Item("Damage+1", 25, 1, 0),
        Item("Damage+2", 50, 2, 0),
        Item("Damage+3", 100, 3, 0),
        Item("Defense+1", 20, 0, 1),
        Item("Defense+1", 40, 0, 2),
        Item("Defense+3", 80, 0, 3),
    )

    class Player(val name: String, var hitPoints: Int, var damage: Int, var armor: Int) {
        override fun toString(): String {
            return "$name: $hitPoints"
        }
    }

    private fun play(
        hitPoints: Int,
        damage: Int,
        armor: Int,
        bossHitPoints: Int,
        bossDamage: Int,
        bossArmor: Int
    ): Boolean {
        val boss = Player("boss", bossHitPoints, bossDamage, bossArmor)
        val self = Player("self", hitPoints, damage, armor)

        var current = self
        var other = boss

        while (true) {
            other.hitPoints -= max(1, current.damage - other.armor)
            if (other.hitPoints <= 0) {
                return other === boss
            }
            other = current.also { current = other }
        }
    }

    override fun part1(): Long {
        val (bossHitPoints, bossDamage, bossArmor) = inputAsList.map { line ->
            line.split(" ")
                .mapNotNull { it.toIntOrNull() }
                .single()
        }
        var minCost = Long.MAX_VALUE

        val playerHitPoints = 100

        for (w in weapons.sortedBy { it.cost }) {
            for (a in armors.sortedBy { it.cost }) {
                for (i in rings.indices) {
                    val r1 = rings[i]
                    for (j in i + 1 until rings.size) {
                        val r2 = rings[j]
                        val totalArmor = w.armor + a.armor + r1.armor + r2.armor
                        val totalDamage = w.damage + a.damage + r1.damage + r2.damage
                        val totalCost = (w.cost + a.cost + r1.cost + r2.cost).toLong()

                        if (totalCost < minCost && play(
                                playerHitPoints,
                                totalDamage,
                                totalArmor,
                                bossHitPoints,
                                bossDamage,
                                bossArmor
                            )
                        ) {
                            minCost = totalCost
                        }
                    }
                }
            }
        }

        return minCost
    }

    override fun part2(): Long {
        val (bossHitPoints, bossDamage, bossArmor) = inputAsList.map { line ->
            line.split(" ")
                .mapNotNull { it.toIntOrNull() }
                .single()
        }
        var maxCost = Long.MIN_VALUE
        val playerHitPoints = 100

        for (w in weapons.sortedBy { it.cost }) {
            for (a in armors.sortedBy { it.cost }) {
                for (i in rings.indices) {
                    val r1 = rings[i]
                    for (j in i + 1 until rings.size) {
                        val r2 = rings[j]
                        val totalArmor = w.armor + a.armor + r1.armor + r2.armor
                        val totalDamage = w.damage + a.damage + r1.damage + r2.damage
                        val totalCost = (w.cost + a.cost + r1.cost + r2.cost).toLong()

                        if (totalCost > maxCost && !play(
                                playerHitPoints,
                                totalDamage,
                                totalArmor,
                                bossHitPoints,
                                bossDamage,
                                bossArmor
                            )
                        ) {
                            maxCost = totalCost
                        }
                    }
                }
            }
        }

        return maxCost
    }

}
