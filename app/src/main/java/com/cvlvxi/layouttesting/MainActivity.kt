package com.cvlvxi.layouttesting

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
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
fun ArtistCard(modifier: Modifier, onClick: () -> Unit) {
  val applicationContext: Context = LocalContext.current
  val padding = 50.dp
  val p = painterResource(id = R.drawable.beb)
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.size(200.dp, 200.dp)
//    modifier=Modifier.fillMaxSize()

  ) {
    Image(painter = p, contentDescription = "dog", modifier=Modifier.size(20.dp))
    Column(
      Modifier
      .padding(padding)
      .clickable(onClick = {
        showToast("You are showing a toast", applicationContext)
      }))
    {
      Text("Dog")
      Text("Cat")
    }
  }
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
//    bottomBar = { ArtistCard(modifier=Modifier.fillMaxSize(), onClick={}) },
  ) {  innerPadding ->
    var entrance = painterResource(id = R.drawable.entrance)
    Box {
      Image(modifier=Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        painter=entrance, contentDescription = "dog")
      Text(text = "Hi there!", modifier = Modifier.padding(innerPadding))
      WalkingPeople(modifier=Modifier.align(Alignment.BottomStart))
    }
  }
}

@Composable
fun WalkingPeople(modifier: Modifier) {
  val lottes = listOf(R.raw.catwalk, R.raw.walking_girl2)
  MovingLottie(lottieId=lottes[0], modifier=modifier, startX=0, endX=500)
  MovingLottie(lottieId=lottes[1], modifier=modifier, startX=300, endX=0)
}


@Composable
fun MovingImage(modifier: Modifier, startX: Int, endX: Int) {
  val beb = painterResource(id = R.drawable.beb)
  var offsetX by remember {mutableStateOf(startX)}
  LaunchedEffect(Unit) {
    animate(
      initialValue = startX.toFloat(),
      targetValue = endX.toFloat(),
      animationSpec = tween(durationMillis = 5000, delayMillis = 1000, easing = LinearEasing),
      block = { value: Float, _: Float -> run { offsetX = value.toInt() } }
    )
  }
  Image(painter = beb, contentDescription="beb", modifier=modifier.size(100.dp).offset(x=offsetX.dp))
}