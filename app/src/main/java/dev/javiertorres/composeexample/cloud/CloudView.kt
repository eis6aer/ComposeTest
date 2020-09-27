package dev.javiertorres.composeexample.cloud

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


enum class CloudState {
    IDLE, ANIMATING, ENDED
}

@Composable
fun CloudView() {
    Stack {
        Canvas(Modifier.preferredHeight(300.dp).preferredWidth(300.dp)) {
            val baseRadius = size.width * 0.35F
            val rightRadius = baseRadius * 0.75F
            val leftRadius = baseRadius * 0.60F

            val verticalCenter = size.height - baseRadius

            drawCircle(
                color = Color.White,
                radius = baseRadius,
                center = Offset(
                    center.x,
                    verticalCenter
                )
            )

            drawCircle(
                color = Color.White,
                radius = leftRadius,
                center = Offset(
                    center.x - (baseRadius),
                    size.height - leftRadius
                )
            )

            drawCircle(
                color = Color.White,
                radius = rightRadius,
                center = Offset(
                    center.x + baseRadius,
                    size.height - rightRadius
                )
            )

            drawRect(
                color = Color.White,
                size = Size(width = baseRadius * 2, height = baseRadius * 0.30f),
                topLeft = Offset(
                    x = baseRadius - (leftRadius),
                    y = size.height - baseRadius * 0.30F
                )
            )
        }
    }
}