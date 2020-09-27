package dev.javiertorres.composeexample.cloud

import android.util.Log
import androidx.compose.animation.core.TransitionState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Stack
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.util.rangeTo
import dev.javiertorres.composeexample.ui.afterCloudAnimation
import dev.javiertorres.composeexample.ui.cloudAnimation
import kotlin.math.pow


enum class CloudState {
    IDLE, ANIMATING, ENDED
}

@Composable
fun AnimatedCloudView(newCloudState: CloudState) {
    val cloudState = remember { mutableStateOf(CloudState.IDLE) } //3

    cloudState.value = newCloudState

    val transitionDefinition = transitionDefinition<CloudState> {

        state(CloudState.IDLE) {
            this[cloudAnimation] = 0F
            this[afterCloudAnimation] = 0
        }

        state(CloudState.ANIMATING) {
            this[cloudAnimation] = 0F
            this[afterCloudAnimation] = 0
        }

        state(CloudState.ENDED) {
            this[cloudAnimation] = 1F
            this[afterCloudAnimation] = 1000
        }

        transition(fromState = CloudState.ANIMATING, toState = CloudState.ENDED) {
            cloudAnimation using tween(durationMillis = 5000)
            afterCloudAnimation using keyframes {
                durationMillis = 5000
                0 at 1000
                1000 at 5000
            }
        }

        transition(fromState = CloudState.ENDED, toState = CloudState.ANIMATING) {
            cloudAnimation using tween(durationMillis = 5000)
            afterCloudAnimation using keyframes {
                durationMillis = 5000
                1000 at 1000
                0 at 5000
            }
        }
    }

    val toState = if (cloudState.value == CloudState.ANIMATING) {
        CloudState.ENDED
    } else {
        CloudState.ANIMATING
    }

    val state = transition(
        definition = transitionDefinition,
        initState = cloudState.value,
        toState = toState
    )

    CloudView(cloudState, state)
}

@Composable
fun CloudView(cloudState: MutableState<CloudState>, state: TransitionState) {
    Stack {
        Canvas(
            Modifier
                .preferredHeight(300.dp)
                .preferredWidth(300.dp)
        ) {
            val baseRadius = size.width * 0.35F * (1 - state[cloudAnimation])
            val baseRadiusUnchanged = size.width * 0.35F
            val rightRadius = baseRadius * 0.75F
            val leftRadius = baseRadius * 0.60F

            val verticalCenter = size.height - baseRadiusUnchanged
            Log.d("After animation", "After animation: ${state[afterCloudAnimation].toFloat() / 1000F}")
            drawCircle(
                color = Color.White,
                radius = baseRadiusUnchanged * (((1 + state[afterCloudAnimation].toFloat() / 1000F)).toDouble()).pow(
                    2.0
                ).toFloat(),
                center = Offset(
                    center.x,
                    verticalCenter - ((baseRadiusUnchanged - baseRadius) * (((1 + state[cloudAnimation].toFloat() / 1000F)).toDouble()).pow(
                        2.0
                    ).toFloat())
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
                size = Size(
                    width = baseRadius * 2,
                    height = baseRadius * 0.30f
                ),
                topLeft = Offset(
                    x = center.x - (baseRadius),
                    y = size.height - baseRadius * 0.30F
                )
            )
        }
    }
}