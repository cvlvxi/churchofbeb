package com.cvlvxi.churchofbeb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.cvlvxi.churchofbeb.areas.ChurchEntrance
import com.cvlvxi.churchofbeb.ui.theme.ChurchOfBebTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { ChurchOfBebTheme { ChurchEntrance() } }
  }
}
