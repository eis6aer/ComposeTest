package dev.javiertorres.composeexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Layout
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.javiertorres.composeexample.ui.ComposeExampleTheme
import dev.javiertorres.composeexample.ui.red

class DiventsActivity : AppCompatActivity() {
    val topics = listOf(
        "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
        "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
        "Religion", "Social sciences", "Technology", "TV", "Writing"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExampleTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            backgroundColor = red,
                            title = {
                                Text(text = "Divents")
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
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(Icons.Filled.Favorite)
                            }
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(Icons.Filled.Favorite)
                            }
                            IconButton(onClick = { /* doSomething() */ }) {
                                Icon(Icons.Filled.Favorite)
                            }
                        }
                    },
                    bodyContent = { innerPadding ->
                        ScrollableRow(modifier = Modifier) {
                            StaggeredGrid(modifier = Modifier.padding(innerPadding), rows = 6) {
                                for (topic in topics) {
                                    Chip(modifier = Modifier.padding(8.dp), text = topic)
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun StaggeredGrid(
        modifier: Modifier = Modifier,
        rows: Int = 3,
        children: @Composable() () -> Unit
    ) {
        Layout(
            modifier = modifier,
            children = children
        ) { measurables, constraints ->

            // Keep track of the width of each row
            val rowWidths = IntArray(rows) { 0 }

            // Keep track of the max height of each row
            val rowMaxHeights = IntArray(rows) { 0 }

            val placeables = measurables.mapIndexed { index, measurable ->

                // Measure each child
                val placeable = measurable.measure(constraints)

                // Track the width and max height of each row
                val row = index % rows
                rowWidths[row] = rowWidths[row] + placeable.width
                rowMaxHeights[row] = kotlin.math.max(rowMaxHeights[row], placeable.height)

                placeable
            }

            // Grid's width is the widest row
            val width = rowWidths.maxOrNull()
                ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

            // Grid's height is the sum of the tallest element of each row
            // coerced to the height constraints
            val height = rowMaxHeights.sumBy { it }
                .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

            // Y of each row, based on the height accumulation of previous rows
            val rowY = IntArray(rows) { 0 }
            for (i in 1 until rows) {
                rowY[i] = rowY[i-1] + rowMaxHeights[i-1]
            }

            layout(width, height) {
                // x cord we have placed up to, per row
                val rowX = IntArray(rows) { 0 }

                placeables.forEachIndexed { index, placeable ->
                    val row = index % rows
                    placeable.placeRelative(
                        x = rowX[row],
                        y = rowY[row]
                    )
                    rowX[row] += placeable.width
                }
            }

        }
    }

    @Composable
    fun Chip(modifier: Modifier = Modifier, text: String) {
        Card(
            modifier = modifier,
            border = BorderStroke(color = Color.Black, width = Dp.Hairline),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                verticalGravity = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.preferredSize(16.dp, 16.dp)
                        .background(color = MaterialTheme.colors.secondary)
                )
                Spacer(Modifier.preferredWidth(4.dp))
                Text(text = text)
            }
        }
    }
}