package com.cvlvxi.layouttesting

import android.graphics.Color.alpha
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cvlvxi.layouttesting.ui.theme.LayoutTestingTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { LayoutTestingTheme { ExampleNoCanvas() } }
  }
}

@Composable
fun ExampleNoCanvas() {
  var nightMode by remember { mutableStateOf(false) }
  Text(text = "dog")
  ScaffoldComposable(
      nightMode = nightMode,
      toggleNightMode = { nightMode = !nightMode },
  )
}

@Composable
fun TopMenu(modifier: Modifier = Modifier.height(10.dp)) {
  Text("Welcome to the Church of the Beb")
}

@Composable
fun ScaffoldComposable(nightMode: Boolean, toggleNightMode: () -> Unit) {
  Scaffold(topBar = { TopMenu() }) {
    val entrance = painterResource(id = R.drawable.entrance)
    val bebface = painterResource(id = R.drawable.bebface)

    BoxWithConstraints {
      Image(
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop,
          painter = entrance,
          contentDescription = "Entrance")

      FlyingLotties(modifier = Modifier.align(Alignment.TopStart), maxWidth = maxWidth)

      SimpleLottie(
          lottieId = R.raw.door_sign1,
          size = 50.dp,
          modifier = Modifier.align(Alignment.Center).offset(y = 200.dp, x = -30.dp),
      )
      RunWalkingLotties(
          numLotties = 8, modifier = Modifier.align(Alignment.BottomStart), maxWidth = maxWidth)

      val dayTime = Color.Yellow

      val nightTime = Color.Black
      val sunMoonAlpha : Float by
        animateFloatAsState(
            if (!nightMode) 0.1f else 0.6f,
            animationSpec =
            tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing))

      val backgroundColor by
          animateColorAsState(
              if (nightMode) nightTime else dayTime,
              animationSpec = tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing))
      Canvas(modifier = Modifier.fillMaxSize()) { drawRect(backgroundColor, alpha = sunMoonAlpha) }

      Box(modifier = Modifier.align(Alignment.TopEnd).offset(x = 50.dp, y = (-50).dp)) {
        val sunAlpha: Float by
            animateFloatAsState(
                if (!nightMode) 0.3f else 0.0f,
                animationSpec =
                    tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing))
        val moonAlpha: Float by
            animateFloatAsState(
                if (!nightMode) 0.0f else 0.5f,
                animationSpec =
                    tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing))
        Image(
            painter = bebface,
            contentDescription = "BebFace",
            modifier =
                Modifier.align(Alignment.Center).alpha(0.7f).size(70.dp).clickable {
                  toggleNightMode()
                })

        SimpleLottie(
            lottieId = R.raw.sun1,
            size = 200.dp,
            modifier = Modifier.align(Alignment.Center).alpha(sunAlpha))
        SimpleLottie(
            lottieId = R.raw.moon3,
            size = 250.dp,
            modifier = Modifier.align(Alignment.Center).alpha(moonAlpha))
      }
    }
  }
}
