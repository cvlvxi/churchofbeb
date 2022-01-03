package com.cvlvxi.layouttesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cvlvxi.layouttesting.ui.theme.LayoutTestingTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      LayoutTestingTheme {
        ExampleNoCanvas()
      }
    }
  }
}

@Composable
fun ExampleNoCanvas() {
  Text(text = "dog")
  ScaffoldComposable()
}

@Composable
fun ExampleWithCanvas(modifier: Modifier) {
  Text("dog")
  DrawCanvas(modifier=modifier)
}

@Composable
fun TopMenu(modifier: Modifier = Modifier.height(10.dp)) {
  Row() {
    Text("Church of beb")
    Text("Go here link")
  }
}

@Composable
fun ScaffoldComposable() {
  Scaffold(topBar = { TopMenu() }) { innerPadding ->
    var entrance = painterResource(id = R.drawable.entrance)
    BoxWithConstraints {
      Image(
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop,
          painter = entrance,
          contentDescription = "dog")
      Text(text = "Hi there!", modifier = Modifier.padding(innerPadding))
      FlyingLotties(modifier = Modifier.align(Alignment.TopStart), maxWidth = maxWidth)
      RunWalkingLotties(
          numLotties = 10, modifier = Modifier.align(Alignment.BottomStart), maxWidth = maxWidth)
    }
  }
}
