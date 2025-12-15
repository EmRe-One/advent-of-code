package tr.emreone.adventofcode

import java.awt.*
import java.awt.geom.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.*

fun drawMinimalistStar(g2d: Graphics2D, x: Int, y: Int, size: Int = 10, filled: Boolean = true) {
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
        g2d.color = Color(ACCENT_GOLD.red, ACCENT_GOLD.green, ACCENT_GOLD.blue, 40)
        g2d.fill(path)
        g2d.color = ACCENT_GOLD
        g2d.fill(path)
    } else {
        g2d.color = BORDER_SUBTLE
        g2d.stroke = BasicStroke(2f)
        g2d.draw(path)
    }
}

fun drawProgressBar(g2d: Graphics2D, x: Int, y: Int, width: Int, height: Int, progress: Float) {
    g2d.color = BG_DARKER
    val bg = RoundRectangle2D.Double(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble(), 6.0, 6.0)
    g2d.fill(bg)

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

fun formatTime(time: String): String {
    val parts = time.split(":")
    return if (parts.size == 3) {
        val (h, m, s) = parts
        if (h.toInt() >= 1) ">${h}h".padStart(5)
        else "${m.padStart(2, '0')}:${s.padStart(2, '0')}"
    } else {
        time.padStart(5)
    }
}

fun generateMinimalistTile(
    day: Int,
    scores: DayScores?,
    hasSolution: Boolean,
    outputFile: File
) {
    val image = BufferedImage(TILE_WIDTH, TILE_HEIGHT, BufferedImage.TYPE_INT_RGB)
    val g2d = image.createGraphics()

    // Premium Rendering für schärferen Text
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB)
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)

    // Hintergrund
    g2d.color = BG_DARK
    g2d.fillRect(0, 0, TILE_WIDTH, TILE_HEIGHT)

    g2d.color = BG_CARD
    val card = RoundRectangle2D.Double(4.0, 4.0, TILE_WIDTH - 8.0, TILE_HEIGHT - 8.0, 16.0, 16.0)
    g2d.fill(card)

    g2d.color = BORDER_SUBTLE
    g2d.stroke = BasicStroke(2f)
    g2d.draw(card)

    // Fonts - alle größer
    // Fonts - alle doppelt so groß für bessere Lesbarkeit
    val dayNumberFont = Font("SansSerif", Font.BOLD, 104)
    val titelLabelFont = Font("SansSerif", Font.PLAIN, 22)
    val partLabelFont = Font("SansSerif", Font.BOLD, 28)
    val dataFont = Font("Monospaced", Font.PLAIN, 22)
    val smallLabelFont = Font("SansSerif", Font.PLAIN, 18)
    val watermarkFont = Font("Monospaced", Font.ITALIC, 64)

    // Tag-Nummer
    g2d.color = TEXT_SECONDARY
    g2d.font = titelLabelFont
    g2d.drawString("$YEAR - Day", 24, 36)

    g2d.color = TEXT_PRIMARY
    g2d.font = dayNumberFont
    g2d.drawString("%02d".format(day), 24, 124)

    // Sterne
    val starsCount = when {
        scores?.time2 != null -> 2
        scores?.time1 != null -> 1
        else -> 0
    }

    for (i in 0 until 2) {
        drawMinimalistStar(g2d, 34 + i * 28, 146, size = 10, filled = i < starsCount)
    }

    // Kotlin Wasserzeichen - groß und transparent
    if (hasSolution) {
        g2d.color = Color(TEXT_MUTED.red, TEXT_MUTED.green, TEXT_MUTED.blue, 25)
        g2d.font = watermarkFont
        val kotlinText = ".kt"
        val metrics = g2d.fontMetrics
        val textWidth = metrics.stringWidth(kotlinText)
        g2d.drawString(kotlinText, 180 - textWidth, 170)
    }

    // Trenner
    g2d.color = BORDER_SUBTLE
    g2d.stroke = BasicStroke(2f)
    g2d.drawLine(190, 20, 190, 180)

    // Parts
    for (part in 1..2) {
        val yBase = if (part == 2) 110 else 20
        val time = if (part == 1) scores?.time1 else scores?.time2
        val rank = if (part == 1) scores?.rank1 else scores?.rank2

        g2d.font = partLabelFont
        g2d.color = if (time != null) TEXT_PRIMARY else TEXT_MUTED
        g2d.drawString("Part $part", 210, yBase + 26)

        if (time != null) {
            g2d.font = smallLabelFont
            g2d.color = TEXT_SECONDARY
            g2d.drawString("TIME", 210, yBase + 50)

            g2d.font = dataFont
            g2d.color = TEXT_PRIMARY
            g2d.drawString(formatTime(time), 290, yBase + 50)

            if (rank != null && rank != "/") {
                g2d.font = smallLabelFont
                g2d.color = TEXT_SECONDARY
                g2d.drawString("RANK", 210, yBase + 72)

                g2d.font = dataFont
                g2d.color = ACCENT_EMERALD
                g2d.drawString(rank.trim().padStart(5), 290, yBase + 72)
            }
        } else {
            g2d.color = BORDER_SUBTLE
            g2d.stroke = BasicStroke(3f)
            val cx = 300
            val cy = yBase + 46
            g2d.drawLine(cx - 10, cy - 10, cx + 10, cy + 10)
            g2d.drawLine(cx - 10, cy + 10, cx + 10, cy - 10)
        }
    }

    g2d.color = BORDER_SUBTLE
    g2d.stroke = BasicStroke(2f)
    g2d.drawLine(210, 100, 386, 100)

    // Progress Bar
    val progress = starsCount / 2f
    drawProgressBar(g2d, 16, TILE_HEIGHT - 16, 168, 6, progress)

    g2d.dispose()
    ImageIO.write(image, "PNG", outputFile)
}

fun main() {
    val outputDir = File("demo_tiles_sharp")
    outputDir.mkdirs()

    println("=== Generiere Optimierte Demo-Kacheln (400x200, scharfer Text) ===\n")

    // Beispiel 1: Vollständig
    generateMinimalistTile(
        day = 1,
        scores = DayScores("00:12:34", "  1234", "00:56:78", "  5678"),
        hasSolution = true,
        outputFile = outputDir.resolve("sharp_completed.png")
    )
    println("✓ Vollständig: demo_tiles_sharp/sharp_completed.png")

    // Beispiel 2: Nur Part 1
    generateMinimalistTile(
        day = 2,
        scores = DayScores("00:23:45", "  2345", null, null),
        hasSolution = true,
        outputFile = outputDir.resolve("sharp_partial.png")
    )
    println("✓ Nur Part 1: demo_tiles_sharp/sharp_partial.png")

    // Beispiel 3: Schnelle Lösung
    generateMinimalistTile(
        day = 3,
        scores = DayScores("00:05:23", "   123", "00:08:47", "   456"),
        hasSolution = true,
        outputFile = outputDir.resolve("sharp_fast.png")
    )
    println("✓ Schnell: demo_tiles_sharp/sharp_fast.png")

    // Beispiel 4: Nur Code
    generateMinimalistTile(
        day = 4,
        scores = null,
        hasSolution = true,
        outputFile = outputDir.resolve("sharp_code_only.png")
    )
    println("✓ Nur Code: demo_tiles_sharp/sharp_code_only.png")

    // Beispiel 5: Nicht begonnen
    generateMinimalistTile(
        day = 5,
        scores = null,
        hasSolution = false,
        outputFile = outputDir.resolve("sharp_empty.png")
    )
    println("✓ Leer: demo_tiles_sharp/sharp_empty.png")

    // Beispiel 6: Langsam (>14h)
    generateMinimalistTile(
        day = 6,
        scores = DayScores(">14h", " 12345", ">15h", " 23456"),
        hasSolution = true,
        outputFile = outputDir.resolve("sharp_slow.png")
    )
    println("✓ Langsam: demo_tiles_sharp/sharp_slow.png")

    println("\n=== Fertig! ===")
    println("Kachelgröße: 400x200 Pixel")
    println("Text-Rendering: LCD RGB für maximale Schärfe")
}
