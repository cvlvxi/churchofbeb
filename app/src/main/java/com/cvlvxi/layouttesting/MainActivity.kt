package com.cvlvxi.layouttesting

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        // A surface container using the 'background' color from the theme
        val modifier = Modifier.fillMaxSize()
        Surface(modifier = modifier, color = MaterialTheme.colors.background) {
          ArtistCard(modifier = modifier, onClick = {

          })
        }
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
//    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.size(300.dp, 200.dp)

  ) {
    Image(painter = p, contentDescription = "dog", modifier=Modifier.size(100.dp))
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
