package com.cvlvxi.churchofbeb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cvlvxi.churchofbeb.ui.theme.ChurchOfBebTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { ChurchOfBebTheme { ChurchOfBebMain() } }
  }
}

@Composable
fun ChurchOfBebMain() {
  val nav = rememberNavController()
  NavHost(
      navController = nav,
      startDestination = Areas.ChurchEntrance.area,
      builder = {
          addChurchEntrance(nav)
          addInsideChurch()
      })
}
