package dev.javiertorres.composeexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Layout
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import dev.javiertorres.composeexample.ui.ComposeExampleTheme
import dev.javiertorres.composeexample.ui.red

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExampleTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            backgroundColor = red,
                            title = {
                                Text(text = "Compose Example Jav T")
                            },
                            actions = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(Icons.Filled.Favorite)
                                }
                            },
                        )
                    },
                    bottomBar = {
                        BottomAppBar {
                            IconButton(onClick = {
                                startActivity(Intent(this@MainActivity, DiventsActivity::class.java))
                            }) {
                                Icon(Icons.Filled.Favorite)
                            }
                            IconButton(onClick = {
                                startActivity(Intent(this@MainActivity, AnimationActivity::class.java))
                            }) {
                                Icon(Icons.Filled.Favorite)
                            }
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(Icons.Filled.Favorite)
                            }
                        }
                    }
                ) { innerPadding ->
                    PhotographerCard(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview
@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable(onClick = { /* Ignoring onClick */ })
            .padding(16.dp)
    ) {
        Surface(
            modifier = modifier.preferredSize(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            // Image goes here
        }
        Column(
            modifier = modifier
                .padding(start = 8.dp)
                .gravity(Alignment.CenterVertically)
        ) {
            Text("Javier Torres", fontWeight = FontWeight.Bold)
            ProvideEmphasis(EmphasisAmbient.current.medium) {
                Text("javiertorres.dev", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun loginView() {
    var text = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalGravity = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(value = text.value, onValueChange = { textValue ->
            text.value = textValue
        }, {
            Text("Enter you user")
        })
        OutlinedTextField(value = text.value, onValueChange = { textValue ->
            text.value = textValue
        }, {
            Text("Enter you password")
        })
    }
}

@Composable
fun JavColumn(
    modifier: Modifier = Modifier,
    children: @Composable() () -> Unit
) {
    Layout(
        modifier = modifier,
        children = children
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each children
            measurable.measure(constraints)
        }

        // Track the y co-ord we have placed children up to
        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}
