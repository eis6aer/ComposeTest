package dev.javiertorres.composeexample

import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import androidx.ui.tooling.preview.PreviewParameter
import dev.javiertorres.composeexample.ui.ComposeExampleTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    loginView()
                }
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
        OutlinedTextField(value = text.value, onValueChange = {
            textValue ->
            text.value = textValue
        }, {
            Text("Enter you user")
        })
        OutlinedTextField(value = text.value, onValueChange = {
                textValue ->
            text.value = textValue
        }, {
            Text("Enter you password")
        })
    }
}
