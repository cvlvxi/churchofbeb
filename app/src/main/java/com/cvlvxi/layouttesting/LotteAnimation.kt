package com.cvlvxi.layouttesting

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun MovingLottie(dLottie: DirectionalLottie, modifier: Modifier, startX: Int, endX: Int) {
    val isReverse = if (dLottie.imageIsLeft) (endX > startX) else (startX > endX)
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(dLottie.lottieId))
    var offsetX by remember {mutableStateOf(startX)}
    LaunchedEffect(Unit) {
        animate(
            initialValue = startX.toFloat(),
            targetValue = endX.toFloat(),
            animationSpec = tween(durationMillis = 5000, delayMillis = 0, easing = LinearEasing),
            block = { value: Float, _: Float -> run { offsetX = value.toInt() } }
        )
    }
    LottieAnimation(
        composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier.size(100.dp).offset(x = offsetX.dp).scale(scaleX= if(isReverse) -1f else 1f, scaleY=1f)
    )
}