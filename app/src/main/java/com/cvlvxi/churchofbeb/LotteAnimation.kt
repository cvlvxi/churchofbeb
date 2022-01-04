package com.cvlvxi.churchofbeb

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import kotlin.random.Random



data class DirectionalLottie(
    val lottieId: Int,
    val imageIsLeft: Boolean,
)

data class movingLottieContainer(
    val lottieId: Int,
    val imageIsLeft: Boolean,
    var start: Float,
    var end: Float,
    var walkingDuration: Int
) {
    var prevOffset: Float? = null

    fun shouldMirror(newOffsetX: Float): Boolean {
        val isRight = (prevOffset?: start) < newOffsetX
        return imageIsLeft == isRight
    }
}

@Composable
fun SimpleLottie(
    lottieId: Int,
    modifier: Modifier,
    size: Dp = 100.dp
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieId))
    LottieAnimation(
        composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
            .size(size)
    )
}

fun generateLotties(lottiePool: List<DirectionalLottie>, howMany: Int, boxMaxWidth: Dp): List<movingLottieContainer> {
    val maxWidth = boxMaxWidth.value.toInt()
    val halfMaxWidth = maxWidth / 2


    val lotties = mutableListOf<movingLottieContainer>()
    (0 until howMany).forEach { _ ->
        val start = Random.nextInt(0, maxWidth)
        val end = if (start < halfMaxWidth) (maxWidth+start) else (0 - start)
        val randIdx = Random.nextInt(0, lottiePool.size)
        lotties.add(movingLottieContainer(
            lottieId=lottiePool[randIdx].lottieId,
            imageIsLeft=lottiePool[randIdx].imageIsLeft,
            start=start.toFloat(),
            end=end.toFloat(),
            walkingDuration = Random.nextInt(5,30) * 1000,
        ))
    }
    return lotties
}




@Composable
fun MoveXLotties(shouldSuspend: Boolean, lotties: List<movingLottieContainer>, modifier: Modifier) {
    lotties.forEach { lottie ->
        val specInfinite = InfiniteRepeatableSpec<Float>(
            animation = tween(
                durationMillis=lottie.walkingDuration,
                delayMillis = 0,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
        val specFinite = TweenSpec<Float>(
            durationMillis=1000,
            delay = 0,
            easing = LinearEasing
        )
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottie.lottieId))
        val offset = remember { Animatable(lottie.start) }

        LaunchedEffect(shouldSuspend) {
            offset.animateTo(
                lottie.end,
                animationSpec = if (!shouldSuspend) specInfinite else specFinite
            )
        }

        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            modifier = modifier
                .size(100.dp)
                .offset(x = offset.value.dp)
                .scale(scaleX = if (lottie.shouldMirror(offset.value)) -1f else 1f, scaleY = 1f)
        )
        lottie.prevOffset = offset.value
    }
}
