package com.example.androidcourse.presentation.ui

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.*

private val SECTOR_COLORS = listOf(
    Color(0xFF4E79A7),
    Color(0xFFF28E2B),
    Color(0xFFE15759),
    Color(0xFF76B7B2),
    Color(0xFF59A14F),
    Color(0xFFEDC948),
    Color(0xFFB07AA1),
    Color(0xFFFF9DA7),
    Color(0xFF9C755F),
    Color(0xFFBAB0AC),
)

private fun Color.activated(): Color = copy(
    red = (red + 0.25f).coerceAtMost(1f),
    green = (green + 0.25f).coerceAtMost(1f),
    blue = (blue + 0.25f).coerceAtMost(1f),
)

@Composable
fun PieChartView(
    sectors: List<Pair<Int, Int>>,
    modifier: Modifier = Modifier,
    chartSize: Dp = 300.dp,
    gapDeg: Float = 2f,
) {
    val total = sectors.sumOf { it.second }
    require(total == 100) { "Сумма всех значений должна быть равна 100, но получено $total" }
    val colors = sectors.mapIndexed { index, _ -> SECTOR_COLORS[index % SECTOR_COLORS.size] }
    var activeIndex by remember { mutableStateOf<Int?>(null) }

    val sweeps = sectors.map { (_, percent) -> percent / 100f * 360f - gapDeg }
    val startAngles = buildList {
        var current = -90f
        sectors.forEachIndexed { i, (_, percent) ->
            add(current)
            current += percent / 100f * 360f
        }
    }

    Box(
        modifier = modifier
            .size(chartSize)
            .pointerInput(Unit) {
                detectTapGestures { activeIndex = null }
            }
    ) {
        Canvas(
            modifier = Modifier
                .size(chartSize)
                .pointerInput(sectors) {
                    detectTapGestures { offset ->
                        val cx = this.size.width / 2f
                        val cy = this.size.height / 2f
                        val radius = minOf(cx, cy) * 0.85f
                        val innerRadius = radius * 0.45f

                        val dx = offset.x - cx
                        val dy = offset.y - cy
                        val dist = sqrt(dx * dx + dy * dy)

                        if (dist < innerRadius || dist > radius) {
                            activeIndex = null
                            return@detectTapGestures
                        }

                        var angleDeg = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
                        if (angleDeg < -90f) angleDeg += 360f
                        var normalizedAngle = angleDeg + 90f
                        if (normalizedAngle < 0f) normalizedAngle += 360f

                        val hit = startAngles.indexOfFirst { startAngle ->
                            val idx = startAngles.indexOf(startAngle)
                            val sweep = sweeps[idx]
                            val relStart = (startAngle + 90f).let { if (it < 0) it + 360f else it }
                            normalizedAngle >= relStart && normalizedAngle <= relStart + sweep
                        }

                        activeIndex = if (hit == activeIndex) null else if (hit >= 0) hit else null
                    }
                }
        ) {
            val cx = size.width / 2f
            val cy = size.height / 2f
            val radius = minOf(cx, cy) * 0.85f
            val innerRadius = radius * 0.45f

            sectors.forEachIndexed { i, (_, percent) ->
                val startAngle = startAngles[i]
                val sweep = sweeps[i]
                val color = if (i == activeIndex) colors[i].activated() else colors[i]

                drawArc(
                    color = color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = true,
                    topLeft = Offset(cx - radius, cy - radius),
                    size = Size(radius * 2, radius * 2),
                )

                drawCircle(color = Color.White, radius = innerRadius, center = Offset(cx, cy))

                val midAngleRad = Math.toRadians((startAngle + sweep / 2f).toDouble())
                val textRadius = (innerRadius + radius) / 2f
                val textX = cx + textRadius * cos(midAngleRad).toFloat()
                val textY = cy + textRadius * sin(midAngleRad).toFloat()

                drawContext.canvas.nativeCanvas.drawText(
                    "$percent%",
                    textX,
                    textY + 14f,
                    Paint().apply {
                        textAlign = android.graphics.Paint.Align.CENTER
                        textSize = if (percent < 5) 24f else 32f
                        setColor(android.graphics.Color.WHITE)
                        isFakeBoldText = true
                        isAntiAlias = true
                        setShadowLayer(4f, 0f, 0f, android.graphics.Color.argb(120, 0, 0, 0))
                    }
                )
            }
        }
    }
}