package tr.emreone.adventofcode

import java.awt.*
import java.awt.geom.*
import java.awt.image.BufferedImage
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant
import javax.imageio.ImageIO
import kotlin.math.*

// ==================== KONFIGURATION ====================

const val YEAR = 2015
const val TILE_WIDTH = 400
const val TILE_HEIGHT = 200
const val CREATE_ALL_DAYS = false
const val SHOW_CHECKMARK_INSTEAD_OF_TIME_RANK = false

val SOURCE_CODE_DIR = File(System.getProperty("user.dir") + "/advent-of-code")
val IMAGE_DIR = SOURCE_CODE_DIR.resolve("aoc_tiles")
val SESSION_COOKIE_PATH = SOURCE_CODE_DIR.resolve("src/main/resources/session.cookie")
val DAY_IMPLEMENTATION_DIR = SOURCE_CODE_DIR.resolve("src/main/kotlin/tr/emreone/adventofcode/year$YEAR")
val CACHE_DIR = SOURCE_CODE_DIR.resolve(".aoc_tiles_cache")

val DAY_PATTERN = Regex("""Day(\d{2})\.kt""")

// ==================== ELEGANTES FARBSCHEMA ====================

// Dunkler Hintergrund mit subtilen Variationen
val BG_DARK = Color(24, 24, 27)           // Fast schwarz
val BG_DARKER = Color(18, 18, 20)         // Noch dunkler für Kontrast
val BG_CARD = Color(39, 39, 42)           // Card-Hintergrund

// Akzentfarben - dezent aber erkennbar
val ACCENT_GOLD = Color(234, 179, 8)      // Gedämpftes Gold
val ACCENT_EMERALD = Color(16, 185, 129)  // Elegantes Grün
val ACCENT_BLUE = Color(59, 130, 246)     // Sanftes Blau

// Text
val TEXT_PRIMARY = Color(250, 250, 250)   // Fast weiß
val TEXT_SECONDARY = Color(161, 161, 170) // Grau für Labels
val TEXT_MUTED = Color(113, 113, 122)     // Noch dezenter

// Borders
val BORDER_SUBTLE = Color(63, 63, 70)     // Subtile Trennlinien
val BORDER_GLOW = Color(82, 82, 91)       // Leicht leuchtend

// ==================== DATENKLASSEN ====================

data class DayScores(
    val time1: String? = null,
    val rank1: String? = null,
    val score1: String? = null,
    val time2: String? = null,
    val rank2: String? = null,
    val score2: String? = null
)

// ==================== LEADERBOARD PARSING ====================

class LeaderboardParser {

    fun parseModernLeaderboard(htmlContent: String): Map<Int, DayScores> {
        if ("You haven't collected any stars... yet." in htmlContent) {
            return emptyMap()
        }

        val pattern = Regex(
            """<span class="leaderboard-daydesc-both">\s*-Part 2-</span>\n(.*?)</pre>""",
            setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE)
        )

        val match = pattern.find(htmlContent) ?: return emptyMap()
        val tableContent = match.groupValues[1].trim()

        val leaderboard = mutableMapOf<Int, DayScores>()

        tableContent.split("\n").forEach { line ->
            val parts = line.trim().split(Regex("\\s+"))
            if (parts.isNotEmpty()) {
                val day = parts[0].toIntOrNull() ?: return@forEach
                val scores = parts.drop(1).map { if (it == "-") null else it }

                when (scores.size) {
                    1 -> leaderboard[day] = DayScores(scores[0], "/", "/")
                    2 -> leaderboard[day] = DayScores(scores[0], "/", "/", scores[1], "/", "/")
                }
            }
        }

        return leaderboard
    }

    fun parseOldLeaderboard(htmlContent: String): Map<Int, DayScores> {
        if ("You haven't collected any stars... yet." in htmlContent) {
            return emptyMap()
        }

        val pattern = Regex(
            """<span class="leaderboard-daydesc-both">\s*Time\s*Rank\s*Score</span>\n(.*?)</pre>""",
            setOf(RegexOption.DOT_MATCHES_ALL, RegexOption.MULTILINE)
        )

        val match = pattern.find(htmlContent) ?: return emptyMap()
        val tableContent = match.groupValues[1].trim()

        val leaderboard = mutableMapOf<Int, DayScores>()

        tableContent.split("\n").forEach { line ->
            val parts = line.trim().split(Regex("\\s+"))
            if (parts.isNotEmpty()) {
                val day = parts[0].toIntOrNull() ?: return@forEach
                val scores = parts.drop(1).map { if (it == "-") null else it }

                when (scores.size) {
                    3 -> leaderboard[day] = DayScores(scores[0], scores[1], scores[2])
                    6 -> leaderboard[day] = DayScores(
                        scores[0], scores[1], scores[2],
                        scores[3], scores[4], scores[5]
                    )
                }
            }
        }

        return leaderboard
    }
}

// ==================== LEADERBOARD DOWNLOAD ====================

class LeaderboardFetcher {

    fun requestLeaderboard(year: Int): Map<Int, DayScores> {
        val leaderboardPath = CACHE_DIR.resolve("leaderboard$year.html")

        if (leaderboardPath.exists()) {
            val parser = LeaderboardParser()
            val leaderboard = if (year < 2025) {
                parser.parseOldLeaderboard(leaderboardPath.readText())
            } else {
                parser.parseModernLeaderboard(leaderboardPath.readText())
            }

            val fileAge = (Instant.now().epochSecond - leaderboardPath.lastModified() / 1000)
            if (fileAge < 30 * 60) {
                println("Leaderboard für $year ist jünger als 30 Minuten, überspringe Download")
                return leaderboard
            }

            val isComplete = leaderboard.size == 25 &&
                    leaderboard.values.all { it.time1 != null && it.time2 != null }
            if (isComplete) {
                println("Leaderboard für $year ist vollständig")
                return leaderboard
            }
        }

        val sessionCookie = SESSION_COOKIE_PATH.readText().trim()
        require(sessionCookie.length == 128) {
            "Session Cookie muss 128 Zeichen lang sein!"
        }

        val url = URL("https://adventofcode.com/$year/leaderboard/self")
        val connection = url.openConnection() as HttpURLConnection
        connection.setRequestProperty("Cookie", "session=$sessionCookie")
        connection.setRequestProperty("User-Agent", "Kotlin AoC Tiles Script")

        val htmlContent = connection.inputStream.bufferedReader().readText()

        CACHE_DIR.mkdirs()
        leaderboardPath.writeText(htmlContent)

        val parser = LeaderboardParser()
        return if (year < 2025) {
            parser.parseOldLeaderboard(htmlContent)
        } else {
            parser.parseModernLeaderboard(htmlContent)
        }
    }
}

// ==================== MINIMALIST TILE GENERATOR ====================

class MinimalistTileGenerator {

    private fun formatTime(time: String): String {
        val cleaned = time.replace("&gt;", "> ")
        return if ("> " in cleaned) {
            cleaned.padStart(5)
        } else {
            val parts = cleaned.split(":")
            if (parts.size == 3) {
                val (h, m, s) = parts
                if (h.toInt() >= 1) "> ${h}h".padStart(5)
                else "${m.padStart(2, '0')}:${s.padStart(2, '0')}"
            } else {
                cleaned.padStart(5)
            }
        }
    }

    private fun drawSoftGlow(g2d: Graphics2D, x: Int, y: Int, width: Int, height: Int, color: Color, intensity: Float = 0.3f) {
        val glowColor = Color(color.red, color.green, color.blue, (255 * intensity).toInt())
        val gradient = RadialGradientPaint(
            Point2D.Float((x + width / 2).toFloat(), (y + height / 2).toFloat()),
            max(width, height).toFloat(),
            floatArrayOf(0f, 1f),
            arrayOf(glowColor, Color(0, 0, 0, 0))
        )
        g2d.paint = gradient
        g2d.fillRect(x - width / 2, y - height / 2, width * 2, height * 2)
    }

    private fun drawMinimalistStar(g2d: Graphics2D, x: Int, y: Int, size: Int = 6, filled: Boolean = true) {
        val numPoints = 5
        val angleStep = Math.PI * 2 / numPoints / 2
        val path = Path2D.Double()

        for (i in 0 until numPoints * 2) {
            val angle = angleStep * i - Math.PI / 2
            val radius = if (i % 2 == 0) size.toDouble() else size * 0.38
            val px = x + cos(angle) * radius
            val py = y + sin(angle) * radius

            if (i == 0) path.moveTo(px, py)
            else path.lineTo(px, py)
        }
        path.closePath()

        if (filled) {
            // Leichter Glow-Effekt
            g2d.color = Color(ACCENT_GOLD.red, ACCENT_GOLD.green, ACCENT_GOLD.blue, 40)
            g2d.fill(path)

            // Stern selbst
            g2d.color = ACCENT_GOLD
            g2d.fill(path)
        } else {
            g2d.color = BORDER_SUBTLE
            g2d.stroke = BasicStroke(1.5f)
            g2d.draw(path)
        }
    }

    private fun drawProgressBar(g2d: Graphics2D, x: Int, y: Int, width: Int, height: Int, progress: Float) {
        // Hintergrund
        g2d.color = BG_DARKER
        val bg = RoundRectangle2D.Double(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble(), 6.0, 6.0)
        g2d.fill(bg)

        // Progress
        if (progress > 0) {
            val progressWidth = (width * progress).toInt()
            val gradient = LinearGradientPaint(
                x.toFloat(), y.toFloat(),
                (x + progressWidth).toFloat(), y.toFloat(),
                floatArrayOf(0f, 1f),
                arrayOf(ACCENT_EMERALD, ACCENT_BLUE)
            )
            g2d.paint = gradient
            val fg = RoundRectangle2D.Double(x.toDouble(), y.toDouble(), progressWidth.toDouble(), height.toDouble(), 6.0, 6.0)
            g2d.fill(fg)
        }
    }

    fun generateDayTile(
        day: Int,
        year: Int,
        hasKotlinSolution: Boolean,
        dayScores: DayScores?,
        outputPath: File
    ) {
        val image = BufferedImage(TILE_WIDTH, TILE_HEIGHT, BufferedImage.TYPE_INT_RGB)
        val g2d = image.createGraphics()

        // Premium Rendering mit LCD-Optimierung für schärferen Text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)

        // === HINTERGRUND ===
        // Basis dunkler Hintergrund
        g2d.color = BG_DARK
        g2d.fillRect(0, 0, TILE_WIDTH, TILE_HEIGHT)

        // Card-ähnlicher Hintergrund mit subtiler Border
        g2d.color = BG_CARD
        val card = RoundRectangle2D.Double(4.0, 4.0, TILE_WIDTH - 8.0, TILE_HEIGHT - 8.0, 16.0, 16.0)
        g2d.fill(card)

        // Subtile Border
        g2d.color = BORDER_SUBTLE
        g2d.stroke = BasicStroke(2f)
        g2d.draw(card)

        // Fonts - alle doppelt so groß für bessere Lesbarkeit
        val dayNumberFont = Font("SansSerif", Font.BOLD, 104)
        val titelLabelFont = Font("SansSerif", Font.PLAIN, 22)
        val partLabelFont = Font("SansSerif", Font.BOLD, 28)
        val dataFont = Font("Monospaced", Font.PLAIN, 22)
        val smallLabelFont = Font("SansSerif", Font.PLAIN, 18)
        val watermarkFont = Font("Monospaced", Font.ITALIC, 64)

        // === TAG-NUMMER (Links) ===
        g2d.color = TEXT_SECONDARY
        g2d.font = titelLabelFont
        g2d.drawString("$year - Day", 24, 36)

        // Große Tag-Nummer
        g2d.color = TEXT_PRIMARY
        g2d.font = dayNumberFont
        val dayStr = "%02d".format(day)
        g2d.drawString(dayStr, 24, 124)

        // Progress indicator (Sterne)
        val starsCount = when {
            dayScores?.time2 != null -> 2
            dayScores?.time1 != null -> 1
            else -> 0
        }

        for (i in 0 until 2) {
            drawMinimalistStar(
                g2d,
                34 + i * 28,
                146,
                size = 10,
                filled = i < starsCount
            )
        }

        // Kotlin Wasserzeichen - groß und transparent unten rechts auf der linken Seite
        if (hasKotlinSolution) {
            g2d.color = Color(TEXT_MUTED.red, TEXT_MUTED.green, TEXT_MUTED.blue, 25) // Sehr transparent
            g2d.font = watermarkFont
            val kotlinText = ".kt"
            val metrics = g2d.fontMetrics
            val textWidth = metrics.stringWidth(kotlinText)
            g2d.drawString(kotlinText, 180 - textWidth, 170)
        }

        // === VERTIKALER TRENNER ===
        g2d.color = BORDER_SUBTLE
        g2d.stroke = BasicStroke(2f)
        g2d.drawLine(190, 20, 190, 180)

        // === RECHTE SEITE (Stats) ===
        for (part in 1..2) {
            val yBase = if (part == 2) 110 else 20
            val time = if (part == 1) dayScores?.time1 else dayScores?.time2
            val rank = if (part == 1) dayScores?.rank1 else dayScores?.rank2

            // Part Label
            g2d.font = partLabelFont
            g2d.color = if (time != null) TEXT_PRIMARY else TEXT_MUTED
            g2d.drawString("Part $part", 210, yBase + 26)

            if (time != null) {
                // Zeit und Rank
                g2d.font = smallLabelFont
                g2d.color = TEXT_SECONDARY
                g2d.drawString("TIME", 210, yBase + 50)

                g2d.font = dataFont
                g2d.color = TEXT_PRIMARY
                val formattedTime = formatTime(time)
                g2d.drawString(formattedTime, 290, yBase + 50)

                if (!SHOW_CHECKMARK_INSTEAD_OF_TIME_RANK && rank != null && rank != "/") {
                    g2d.font = smallLabelFont
                    g2d.color = TEXT_SECONDARY
                    g2d.drawString("RANK", 210, yBase + 72)

                    g2d.font = dataFont
                    g2d.color = ACCENT_EMERALD
                    g2d.drawString(rank.trim().padStart(5), 290, yBase + 72)
                }

                // Checkmark für Completion
                if (SHOW_CHECKMARK_INSTEAD_OF_TIME_RANK) {
                    g2d.color = ACCENT_EMERALD
                    g2d.stroke = BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
                    g2d.drawLine(350, yBase + 60, 360, yBase + 70)
                    g2d.drawLine(360, yBase + 70, 376, yBase + 44)
                }
            } else {
                // Nicht abgeschlossen - minimalistisches X
                g2d.color = BORDER_SUBTLE
                g2d.stroke = BasicStroke(3f)
                val cx = 300
                val cy = yBase + 46
                g2d.drawLine(cx - 10, cy - 10, cx + 10, cy + 10)
                g2d.drawLine(cx - 10, cy + 10, cx + 10, cy - 10)
            }
        }

        // Horizontaler Trenner zwischen Parts
        g2d.color = BORDER_SUBTLE
        g2d.stroke = BasicStroke(2f)
        g2d.drawLine(210, 100, 386, 100)

        // === OVERALL PROGRESS BAR (unten) ===
        val progress = starsCount / 2f
        drawProgressBar(g2d, 16, TILE_HEIGHT - 16, 168, 6, progress)

        g2d.dispose()

        // Speichern
        outputPath.parentFile?.mkdirs()
        ImageIO.write(image, "PNG", outputPath)
    }
}

// ==================== HAUPT-LOGIK ====================

fun findKotlinSolutions(): Map<Int, Boolean> {
    val solutions = mutableMapOf<Int, Boolean>()

    if (!DAY_IMPLEMENTATION_DIR.exists()) {
        println("Warnung: Verzeichnis $DAY_IMPLEMENTATION_DIR existiert nicht")
        return solutions
    }

    DAY_IMPLEMENTATION_DIR.listFiles()?.forEach { file ->
        DAY_PATTERN.find(file.name)?.let { match ->
            val day = match.groupValues[1].toInt()
            solutions[day] = true
        }
    }

    return solutions
}

fun main() {
    println("=== Advent of Code Tile Generator (Minimalist Edition) ===")
    println("Jahr: $YEAR")
    println()

    // Leaderboard laden
    val fetcher = LeaderboardFetcher()
    val leaderboard = try {
        fetcher.requestLeaderboard(YEAR)
    } catch (e: Exception) {
        println("Fehler beim Laden des Leaderboards: ${e.message}")
        emptyMap()
    }

    // Lösungen finden
    val solutions = findKotlinSolutions()

    // Maximaler Tag
    val maxDay = if (CREATE_ALL_DAYS) 25 else maxOf(
        solutions.keys.maxOrNull() ?: 0,
        leaderboard.keys.maxOrNull() ?: 0
    )

    if (maxDay == 0) {
        println("Keine Lösungen oder Leaderboard-Einträge gefunden!")
        return
    }

    // Kacheln generieren
    val generator = MinimalistTileGenerator()
    val imageDir = IMAGE_DIR.resolve(YEAR.toString())

    var generatedCount = 0
    for (day in 1..maxDay) {
        val outputPath = imageDir.resolve("%02d.png".format(day))
        val hasSolution = solutions[day] ?: false
        val scores = leaderboard[day]

        generator.generateDayTile(day, YEAR, hasSolution, scores, outputPath)
        generatedCount++

        val status = when {
            scores?.time2 != null -> "★★"
            scores?.time1 != null -> "★ "
            hasSolution -> "kt"
            else -> "──"
        }
        println("Tag %2d: %s -> %s".format(day, status, outputPath.relativeTo(SOURCE_CODE_DIR)))
    }

    println()
    println("=== Fertig! ===")
    println("$generatedCount Kacheln generiert in: ${imageDir.relativeTo(SOURCE_CODE_DIR)}")

    val stars = leaderboard.values.sumOf {
        (if (it.time1 != null) 1 else 0) + (if (it.time2 != null) 1 else 0)
    }
    println("Gesamt: $stars ⭐")
}
