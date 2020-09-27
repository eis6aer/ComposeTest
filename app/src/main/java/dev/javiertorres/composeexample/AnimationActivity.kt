package dev.javiertorres.composeexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.compose.foundation.Box
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.javiertorres.composeexample.cloud.AnimatedCloudView
import dev.javiertorres.composeexample.cloud.CloudState
import dev.javiertorres.composeexample.cloud.CloudView
import dev.javiertorres.composeexample.ui.ComposeExampleTheme

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExampleTheme {
                Scaffold(
                    backgroundColor = Color.Cyan
                ) {
                    mainButtons()
                }
            }
        }
    }

    @Composable
    fun mainButtons() {
        val cloudState = remember { mutableStateOf(CloudState.IDLE) }

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            horizontalGravity = Alignment.CenterHorizontally
        ) {
            Text(
                "Cloud Sync",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                horizontalGravity = Alignment.CenterHorizontally
            ) {
                AnimatedCloudView(cloudState.value)
                Button(onClick = {
                    if (cloudState.value == CloudState.ANIMATING) {
                        cloudState.value = CloudState.ENDED
                    } else
                        cloudState.value = CloudState.ANIMATING
                }) {
                    Text("Begin Upload")
                }
            }
        }
    }
}