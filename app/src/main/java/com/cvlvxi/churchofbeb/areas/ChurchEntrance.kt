package com.cvlvxi.churchofbeb.areas

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cvlvxi.churchofbeb.*
import com.cvlvxi.churchofbeb.R
import kotlin.random.Random

@Composable
fun ChurchEntrance(navInsideHandle: () -> Unit, modifier: Modifier) {
  var nightMode by remember { mutableStateOf(false) }
  Text(text = "dog")
  ScaffoldComposable(
      nightMode = nightMode,
      toggleNightMode = { nightMode = !nightMode },
      modifier = modifier,
      navInsideHandle = navInsideHandle)
}

@Composable
fun ScaffoldComposable(
    nightMode: Boolean,
    toggleNightMode: () -> Unit,
    navInsideHandle: () -> Unit,
    modifier: Modifier
) {
  Scaffold(modifier = modifier) {
    val entrance = painterResource(id = R.drawable.entrance)
    val bebface = painterResource(id = R.drawable.bebface)
    val nightMode2 by rememberUpdatedState(nightMode)

    BoxWithConstraints {
      val simpleFloatTween =
          TweenSpec<Float>(durationMillis = 3000, delay = 0, easing = LinearEasing)
      val simpleColorTween =
          TweenSpec<Color>(durationMillis = 3000, delay = 0, easing = LinearEasing)
      val dayTime = Color.Yellow
      val nightTime = Color.Black
      val dayToNightAlpha: Float by
          animateFloatAsState(if (!nightMode) 0.1f else 0.65f, animationSpec = simpleFloatTween)
      val bebFaceAlpha: Float by
          animateFloatAsState(if (!nightMode) 0.7f else 0.8f, animationSpec = simpleFloatTween)
      val backgroundColor by
          animateColorAsState(
              if (nightMode) nightTime else dayTime, animationSpec = simpleColorTween)
      var initDisabled by remember { mutableStateOf(true) }

      Image(
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop,
          painter = entrance,
          contentDescription = "Entrance")

      FlyingLotties(nightMode2, modifier = Modifier.align(Alignment.TopStart), maxWidth = maxWidth)

      // Door Handle
      Box(modifier = Modifier.align(Alignment.Center).offset(y = 200.dp, x = (-30).dp)) {
        TransitionAlphaSimpleLottie(
            lottieId = R.raw.door_sign1,
            size = 50.dp,
            alphaStart = 1.0f,
            alphaEnd = 0.0f,
            condition = nightMode,
            modifier =
                Modifier.clickable {
                  if (!nightMode) {
                    navInsideHandle()
                  }
                })
        TransitionAlphaSimpleLottie(
            lottieId = R.raw.closed_sign1,
            size = 50.dp,
            alphaStart = 0.0f,
            alphaEnd = 1.0f,
            condition = nightMode,
            modifier = Modifier)
      }

      // Walking Lotties
      DayMovingLotties(
          nightMode2,
          numLotties = 10,
          modifier = Modifier.align(Alignment.BottomStart),
          maxWidth = maxWidth)

      // Night Lotties
      NightCloudLotties(
          !nightMode2,
          modifier = Modifier.align(Alignment.TopStart),
          maxWidth = maxWidth,
          initDisabled = initDisabled)
      // Night Running Lotties
      NightMovingLotties(
          !nightMode2,
          modifier = Modifier.align(Alignment.BottomStart),
          maxWidth = maxWidth,
          initDisabled = initDisabled,
          numLotties = 10)

      Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(backgroundColor, alpha = dayToNightAlpha)
      }

      // Sun and Moon and Beb Container
      Box(modifier = Modifier.align(Alignment.TopEnd).offset(x = 50.dp, y = (-50).dp)) {
        TransitionAlphaSimpleLottie(
            lottieId = R.raw.sun1,
            size = 200.dp,
            alphaStart = 0.3f,
            alphaEnd = 0.0f,
            condition = nightMode,
            modifier = Modifier.align(Alignment.Center))
        TransitionAlphaSimpleLottie(
            lottieId = R.raw.moon3,
            size = 250.dp,
            alphaStart = 0.0f,
            alphaEnd = 0.5f,
            condition = nightMode,
            modifier = Modifier.align(Alignment.Center))
        Image(
            painter = bebface,
            contentDescription = "BebFace",
            modifier =
                Modifier.align(Alignment.Center).alpha(bebFaceAlpha).size(70.dp).clickable {
                  toggleNightMode()
                  initDisabled = false
                })
      }
      if (nightMode) {
        FireworkLotties(modifier = Modifier.align(Alignment.Center).offset(y = (-200).dp))
      }
    }
  }
}

@Composable
fun FlyingLotties(shouldSuspend: Boolean, modifier: Modifier, maxWidth: Dp) {
  val lottiePool =
      listOf(
          DirectionalLottie(R.raw.birds_flying1, false),
          DirectionalLottie(R.raw.cloudday1, false, size = Random.nextInt(100, 200).dp))
  val lotties = generateLotties(lottiePool, 3, maxWidth, true)
  MovingLotties(shouldSuspend, lotties, modifier = modifier)
}

@Composable
fun NightCloudLotties(
    shouldSuspend: Boolean,
    modifier: Modifier,
    maxWidth: Dp,
    initDisabled: Boolean
) {
  val lottiePool =
      listOf(
          DirectionalLottie(R.raw.cloudnight1, false, size = Random.nextInt(100, 200).dp),
      )
  val lotties = generateLotties(lottiePool, 10, maxWidth, true)
  MovingLotties(
      shouldSuspend, lotties, modifier = modifier, delayMs = 3000, initiallyDisabled = initDisabled)
}

@Composable
fun RunRunningLotties(shouldSuspend: Boolean, numLotties: Int, modifier: Modifier, maxWidth: Dp) {
  val lottiePool =
      listOf(
          DirectionalLottie(R.raw.walking_cat1, false),
          DirectionalLottie(R.raw.walking_girl1, false),
          DirectionalLottie(R.raw.walking_girl2, false),
          DirectionalLottie(R.raw.walking_girl3, true),
          DirectionalLottie(R.raw.walking_guy1, false),
          DirectionalLottie(R.raw.walking_guy2, false),
          DirectionalLottie(R.raw.walking_guy3, true),
          DirectionalLottie(R.raw.walking_duck1, false),
          DirectionalLottie(R.raw.walking_orange1, false),
          DirectionalLottie(R.raw.walking_peach1, false),
      )
  val lotties = generateLotties(lottiePool, numLotties, maxWidth, false)
  MovingLotties(shouldSuspend, lotties, modifier = modifier)
}

@Composable
fun NightMovingLotties(
    shouldSuspend: Boolean,
    numLotties: Int,
    modifier: Modifier,
    maxWidth: Dp,
    initDisabled: Boolean
) {
  val lottiePool =
      listOf(
          DirectionalLottie(R.raw.running_lizard1, false),
          DirectionalLottie(R.raw.running_man2, false),
          DirectionalLottie(R.raw.running_man1, false),
          DirectionalLottie(R.raw.running_coffee1, false),
          DirectionalLottie(R.raw.running_chips1, false),
          DirectionalLottie(R.raw.running_coffee1, false),
      )
  val lotties = generateLotties(lottiePool, numLotties, maxWidth, true)
  MovingLotties(shouldSuspend, lotties, modifier = modifier, delayMs = 3000, initiallyDisabled = initDisabled)
}

@Composable
fun DayMovingLotties(shouldSuspend: Boolean, numLotties: Int, modifier: Modifier, maxWidth: Dp) {
  val lottiePool =
      listOf(
          DirectionalLottie(R.raw.walking_cat1, false),
          DirectionalLottie(R.raw.walking_girl1, false),
          DirectionalLottie(R.raw.walking_girl2, false),
          DirectionalLottie(R.raw.walking_girl3, true),
          DirectionalLottie(R.raw.walking_guy1, false),
          DirectionalLottie(R.raw.walking_guy2, false),
          DirectionalLottie(R.raw.walking_guy3, true),
          DirectionalLottie(R.raw.walking_duck1, false),
          DirectionalLottie(R.raw.walking_orange1, false),
          DirectionalLottie(R.raw.walking_peach1, false),
      )
  val lotties = generateLotties(lottiePool, numLotties, maxWidth, true)
  MovingLotties(shouldSuspend, lotties, modifier = modifier)
}

@Composable
fun FireworkLotties(modifier: Modifier) {
  val lottiePool =
      listOf(
          //          DirectionalLottie(R.raw.rocket1, false),
          DirectionalLottie(R.raw.fireworks1, false),
          DirectionalLottie(R.raw.fireworks2, false),
          DirectionalLottie(R.raw.fireworks3, false),
          DirectionalLottie(R.raw.fireworks4, false),
      )
  val lotties =
      listOf(
          MovingLottieContainer(
              lottiePool[0].lottieId,
              imageIsLeft = lottiePool[0].imageIsLeft,
              start = 0.0f,
              end = 100.0f,
              size = 200.dp,
              walkingDuration = 2000,
          ),
          MovingLottieContainer(
              lottiePool[1].lottieId,
              imageIsLeft = lottiePool[0].imageIsLeft,
              start = 50.0f,
              end = 55.0f,
              size = 100.dp,
              walkingDuration = 2000,
          ))

  MovingLotties(false, lotties, modifier = modifier, isUp = true)
}
