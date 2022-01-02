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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
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
    BoxWithConstraints{
      Image(modifier=Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        painter=entrance, contentDescription = "dog")
      Text(text = "Hi there!", modifier = Modifier.padding(innerPadding))
      Walking(modifier=Modifier.align(Alignment.BottomStart), boxWidth=maxWidth)
    }
  }
}

data class DirectionalLottie(val lottieId: Int, val imageIsLeft: Boolean)

@Composable
fun Walking(modifier: Modifier, boxWidth: Dp) {
  val maxWidth = boxWidth.value.toInt()
  val halfMaxWidth = maxWidth / 2
  val lotties= listOf(
    DirectionalLottie(R.raw.catwalk, false),
    DirectionalLottie(R.raw.walking_girl2, false),
    DirectionalLottie(R.raw.walking_girl3, true),
    DirectionalLottie(R.raw.walking_guy3, true),
  )
  for (i in 0..10) {
    val randStart = Random.nextInt(0, maxWidth)
    val endX = if (randStart < halfMaxWidth) maxWidth+randStart else 0-randStart

    MovingLottie(
      dLottie=lotties[i % lotties.size],
      modifier=modifier,
      startX=randStart,
      endX=endX
    )
  }
}
