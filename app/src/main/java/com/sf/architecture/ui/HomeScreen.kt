package com.sf.architecture.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sf.architecture.ui.theme.ArchitectureTheme

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    ArchitectureTheme {
        Greeting("Android")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}