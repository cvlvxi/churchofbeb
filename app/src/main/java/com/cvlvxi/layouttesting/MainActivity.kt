package com.cvlvxi.layouttesting

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import com.cvlvxi.layouttesting.ui.theme.LayoutTestingTheme


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      LayoutTestingTheme {
        Text(text="dog")
        ScaffoldComposable()
      }
    }
  }
}

fun showToast(text: String, applicationContext: Context) {
  val duration = Toast.LENGTH_SHORT
  val toast = Toast.makeText(applicationContext, text, duration)
  toast.show()
}

@Composable
fun TopMenu(modifier: Modifier =Modifier.height(10.dp)) {
  Row() {
    Text("Church of beb")
    Text("Go here link")
  }
}

@Composable
fun ScaffoldComposable() {
  Scaffold(
    topBar = { TopMenu() }
  ) {  innerPadding ->
    var entrance = painterResource(id = R.drawable.entrance)
    BoxWithConstraints{
      Image(modifier=Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        painter=entrance, contentDescription = "dog")
      Text(text = "Hi there!", modifier = Modifier.padding(innerPadding))
      val (lotties, offsetz) = generateLotties(6,  maxWidth)
      WalkingLotties(lotties, offsetz, modifier = Modifier.align(Alignment.BottomStart))
    }
  }
}


