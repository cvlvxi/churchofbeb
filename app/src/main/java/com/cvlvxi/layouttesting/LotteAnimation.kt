package com.cvlvxi.layouttesting

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.launch
import kotlin.random.Random

data class DirectionalLottie(
    val lottieId: Int,
    val imageIsLeft: Boolean,
)

data class Lottie(
    val lottieId: Int,
    val imageIsLeft: Boolean,
    val startX: Int,
    val endX: Int,
    val walkingDuration: Int,
) {
    fun isReverse(): Boolean {
        return if (this.imageIsLeft) (this.endX > this.startX) else (this.startX > this.endX)
    }
}

fun generateLotties(howMany: Int, boxMaxWidth: Dp): Pair<List<Lottie>, List<Int>> {
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

    val offsetList = mutableListOf<Int>()
    val lottieList = mutableListOf<Lottie>()
    (0..howMany-1).forEach {
        val startX = Random.nextInt(0, maxWidth)
        val endX = if (startX < halfMaxWidth) (maxWidth+startX) else (0 - startX)
        offsetList.add(startX)
        val randIdx = Random.nextInt(0, lottieIds.size)
        lottieList.add(Lottie(
            lottieId=lottieIds[randIdx].lottieId,
            imageIsLeft=lottieIds[randIdx].imageIsLeft,
            startX=startX,
            endX=endX,
            walkingDuration = Random.nextInt(5,20)
        ))
    }
    return Pair(lottieList, offsetList)
}


@Composable
fun WalkingLotties(lotties: List<Lottie>, initOffsets: List<Int>, modifier: Modifier) {
    val offsets = remember { mutableStateListOf<Int>() }
    var finished = remember { mutableStateListOf<Boolean>() }
    initOffsets.forEach {
        offsets.add(it)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        lotties.forEachIndexed { index, lottie ->
            scope.launch {
                animate(
                    initialValue = lottie.startX.toFloat(),
                    targetValue = lottie.endX.toFloat(),
                    animationSpec = tween(
                        durationMillis = lottie.walkingDuration * 1000,
                        delayMillis = 0,
                        easing = LinearEasing
                    ),
                    block = { value: Float, _: Float ->
                        run {
                            offsets[index] = value.toInt()
                        }
                    }
                )
            }
        }
    }
    lotties.forEachIndexed { index, lottie ->
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottie.lottieId))
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            modifier = modifier
                .size(100.dp)
                .offset(x = offsets[index].dp)
                .scale(scaleX = if (lottie.isReverse()) -1f else 1f, scaleY = 1f)
        )

    }
}

