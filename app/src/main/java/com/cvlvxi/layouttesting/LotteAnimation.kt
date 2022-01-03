package com.cvlvxi.layouttesting

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

data class Lottie(
    val lottieId: Int,
    val imageIsLeft: Boolean,
    var startX: Float,
    var endX: Float,
    var walkingDuration: Int
) {
    val initDirectionRight: Boolean = this.endX > this.startX
    val initDierctionLeft: Boolean = this.startX > this.endX
    val shouldReverse: Boolean = if (this.imageIsLeft) (this.initDirectionRight) else (this.initDierctionLeft)
    var prevOffsetX: Float? = null

    fun shouldMirror(newOffsetX: Float): Boolean? {
        if (prevOffsetX == null) {
            return null
        }
        val isRight = prevOffsetX!! < newOffsetX

//        return (imageIsLeft && !isRight) || (!imageIsLeft && isRight)
//        return imageIsLeft != isRight
        return imageIsLeft xor isRight
    }
}

fun generateLotties(howMany: Int, boxMaxWidth: Dp): List<Lottie> {
    val maxWidth = boxMaxWidth.value.toInt()
    val halfMaxWidth = maxWidth / 2
    val lottieIds= listOf(
        DirectionalLottie(R.raw.catwalk, false),
        DirectionalLottie(R.raw.walking_girl2, false),
        DirectionalLottie(R.raw.walking_girl3, true),
        DirectionalLottie(R.raw.walking_guy3, true),
        DirectionalLottie(R.raw.walking_girl1, false),
        DirectionalLottie(R.raw.walking_guy1, false),
    )

    val lottieList = mutableListOf<Lottie>()
    (0 until howMany).forEach { _ ->
        val startX = Random.nextInt(0, maxWidth)
        val endX = if (startX < halfMaxWidth) (maxWidth+startX) else (0 - startX)
        val randIdx = Random.nextInt(0, lottieIds.size)
        lottieList.add(Lottie(
            lottieId=lottieIds[randIdx].lottieId,
            imageIsLeft=lottieIds[randIdx].imageIsLeft,
            startX=startX.toFloat(),
            endX=endX.toFloat(),
            walkingDuration = Random.nextInt(5,30) * 1000,
        ))
    }
    return lottieList
}


@Composable
fun WalkingLotties(lotties: List<Lottie>, modifier: Modifier, boxMaxWidth: Dp, boxMinWidth: Dp) {
    lotties.forEach { lottie ->
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottie.lottieId))
        val startX by remember { mutableStateOf(lottie.startX) }
        val infiniteTransition = rememberInfiniteTransition()

        val offsetX by infiniteTransition.animateFloat(
            initialValue = startX,
            targetValue = lottie.endX,

            animationSpec = InfiniteRepeatableSpec(
                animation = tween(
//                    durationMillis=lottie.walkingDuration,
                    durationMillis=5000,
                    delayMillis = 0,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
        val shouldReverse = lottie.shouldMirror(offsetX)?:lottie.shouldReverse
        lottie.prevOffsetX = offsetX
        println("Prev OffsetX ${lottie.prevOffsetX}")
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            modifier = modifier
                .size(100.dp)
                .offset(x = offsetX.toInt().dp)
                .scale(scaleX = if (shouldReverse) 1f else -1f, scaleY = 1f)
        )
    }
}

