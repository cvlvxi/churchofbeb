package com.cvlvxi.churchofbeb

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import kotlin.random.Random

data class DirectionalLottie(val lottieId: Int, val imageIsLeft: Boolean, val size: Dp = 100.dp)

data class MovingLottieContainer(
    val lottieId: Int,
    val imageIsLeft: Boolean,
    var start: Float,
    var end: Float,
    var walkingDuration: Int,
    var size: Dp = 100.dp
) {
  var prevOffset: Float? = null

  fun shouldMirror(newOffsetX: Float): Boolean {
    val isRight = (prevOffset ?: start) < newOffsetX
    return imageIsLeft == isRight
  }
  fun addSize(size: Dp) {
    this.size = size
  }
}

@Composable
fun SimpleLottie(lottieId: Int, modifier: Modifier, size: Dp = 100.dp) {
  val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieId))
  LottieAnimation(
      composition, iterations = LottieConstants.IterateForever, modifier = modifier.size(size))
}

data class TransitionAlphaLottieContainer(
    val lottieId: Int,
    val size: Dp,
    val alphaStart: Float,
    val alphaEnd: Float,
    val condition: Boolean,
    val spec: AnimationSpec<Float>? =
        TweenSpec(durationMillis = 3000, delay = 0, easing = LinearEasing),
    val modifier: Modifier?,
)

@Composable
fun TransitionAlphaSimpleLottie(
    lottieId: Int,
    size: Dp = 100.dp,
    alphaStart: Float,
    alphaEnd: Float,
    condition: Boolean,
    spec: AnimationSpec<Float> =
        TweenSpec<Float>(durationMillis = 3000, delay = 0, easing = LinearEasing),
    modifier: Modifier,
) {
  val alpha: Float by
      animateFloatAsState(if (!condition) alphaStart else alphaEnd, animationSpec = spec)
  SimpleLottie(lottieId = lottieId, size = size, modifier = modifier.alpha(alpha))
}

fun generateLotties(
    lottiePool: List<DirectionalLottie>,
    howMany: Int,
    boxMaxWidth: Dp,
    fromEnds: Boolean,
): List<MovingLottieContainer> {
  val maxWidth = boxMaxWidth.value.toInt()
  val halfMaxWidth = maxWidth / 2

  val lotties = mutableListOf<MovingLottieContainer>()
  (0 until howMany).forEach { _ ->
    val start: Int
    val end: Int
    val startRange = Pair(-500, -50)
    val endRange = Pair(maxWidth+50, maxWidth+500)
//    val startRange = Pair(0, 50)
//    val endRange = Pair(100, 300)
    if (fromEnds) {
      val beforeStart = Random.nextInt(startRange.first, startRange.second)
      val afterEnd = Random.nextInt(endRange.first, endRange.second)
      if (Random.nextBoolean()) {
        start = beforeStart
        end = afterEnd
      } else {
        start = afterEnd
        end = beforeStart
      }
    } else {
      start = Random.nextInt(0, maxWidth)
      end = if (start < halfMaxWidth) (maxWidth + start) else (0 - start)
    }
    val randIdx = Random.nextInt(0, lottiePool.size)
    var m =
        lotties.add(
            MovingLottieContainer(
                lottieId = lottiePool[randIdx].lottieId,
                imageIsLeft = lottiePool[randIdx].imageIsLeft,
                start = start.toFloat(),
                end = end.toFloat(),
                walkingDuration = Random.nextInt(5, 12) * 1000,
                size = lottiePool[randIdx].size))
  }
  return lotties
}

@Composable
fun MovingLotties(
    shouldSuspend: Boolean,
    lotties: List<MovingLottieContainer>,
    modifier: Modifier,
    isUp: Boolean = false,
    infiniteRepeatMode: RepeatMode = RepeatMode.Reverse,
    delayMs: Int = 0,
    initiallyDisabled: Boolean = false
) {
  lotties.forEach { lottie ->
    val specInfinite =
        InfiniteRepeatableSpec<Float>(
            animation =
                tween(
                    durationMillis = lottie.walkingDuration,
                    delayMillis = delayMs,
                    easing = LinearEasing),
            repeatMode = infiniteRepeatMode)
    val specFinite = TweenSpec<Float>(durationMillis = 1000, delay = 0, easing = LinearEasing)
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottie.lottieId))
    val offset  = remember { Animatable(lottie.start) }

    println(offset.value)




    LaunchedEffect(shouldSuspend) {
        if (!initiallyDisabled) {
            offset.animateTo(lottie.end, animationSpec = if (!shouldSuspend) specInfinite else specFinite)
        }
    }
    if (!isUp) {
      LottieAnimation(
          composition,
          iterations = LottieConstants.IterateForever,
          modifier =
              modifier
                  .size(lottie.size)
                  .offset(x = offset.value.dp)
                  .scale(scaleX = if (lottie.shouldMirror(offset.value)) -1f else 1f, scaleY = 1f))
      lottie.prevOffset = offset.value
    } else {
      LottieAnimation(
          composition,
          iterations = LottieConstants.IterateForever,
          modifier = modifier.size(lottie.size).offset(y = -offset.value.dp))
    }
  }
}
