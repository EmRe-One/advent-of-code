package tr.emreone.adventofcode.year2015

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import tr.emreone.kotlin_utils.Resources
import tr.emreone.kotlin_utils.automation.Day
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.forEach

class Day12 : Day(
    12,
    2015,
    "JSAbacusFramework.io",
    session = Resources.resourceAsString("session.cookie")
) {

    private fun sumInJsonElement(element: JsonElement, filterReds: Boolean = false): Int {
        var sum = 0

        if (element is JsonObject) {
            if (filterReds && element.values.contains(JsonPrimitive("red"))) {
                return 0
            }

            element.entries.forEach { (_, element) ->
                sum += sumInJsonElement(element, filterReds)
            }
        }
        else if (element is JsonArray) {
            element.forEach { item ->
                sum += sumInJsonElement(item, filterReds)
            }
        }
        else if (element is JsonPrimitive && element.intOrNull != null) {
            sum += element.int
        }

        return sum
    }

    override fun part1(): Int {
        val json = Json
        val deserializedToTree: JsonElement = json.parseToJsonElement(inputAsString)

        return sumInJsonElement(deserializedToTree)
    }

    override fun part2(): Int {
        val json = Json
        val deserializedToTree: JsonElement = json.parseToJsonElement(inputAsString)

        return sumInJsonElement(deserializedToTree, true)
    }

}
