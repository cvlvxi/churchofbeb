package com.cvlvxi.churchofbeb.areas

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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

@Composable
fun FlyingLotties(shouldSuspend: Boolean, modifier: Modifier, maxWidth: Dp) {
    val lottiePool= listOf(
        DirectionalLottie(R.raw.birds_flying1, false)
    )
    val lotties = generateLotties(lottiePool, 2,  maxWidth)
    MoveXLotties(shouldSuspend, lotties, modifier = modifier)
}


@Composable
fun RunWalkingLotties(shouldSuspend: Boolean, numLotties: Int, modifier: Modifier, maxWidth: Dp) {
    val lottiePool= listOf(
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
    val lotties = generateLotties(lottiePool, numLotties,  maxWidth)
    MoveXLotties(shouldSuspend, lotties, modifier = modifier)
}

@Composable
fun ChurchEntrance() {
    var nightMode by remember { mutableStateOf(false) }
    Text(text = "dog")
    ScaffoldComposable(
        nightMode = nightMode,
        toggleNightMode = { nightMode = !nightMode },
    )
}

@Composable
fun TopMenu(modifier: Modifier = Modifier.height(10.dp)) {
    Text("Welcome to the Church of the Beb")
}

@Composable
fun ScaffoldComposable(nightMode: Boolean, toggleNightMode: () -> Unit) {
    //  Scaffold(topBar = { TopMenu() }) {

    Scaffold() {
        val entrance = painterResource(id = R.drawable.entrance)
        val bebface = painterResource(id = R.drawable.bebface)
        val nightMode2 by rememberUpdatedState(nightMode)

        BoxWithConstraints {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = entrance,
                contentDescription = "Entrance")

            FlyingLotties(nightMode2, modifier = Modifier.align(Alignment.TopStart), maxWidth = maxWidth)

            SimpleLottie(
                lottieId = R.raw.door_sign1,
                size = 50.dp,
                modifier = Modifier.align(Alignment.Center).offset(y = 200.dp, x = -30.dp),
            )
            RunWalkingLotties(
                nightMode2, numLotties = 8, modifier = Modifier.align(Alignment.BottomStart), maxWidth = maxWidth)

            val dayTime = Color.Yellow

            val nightTime = Color.Black

            val sunMoonAlpha: Float by
            animateFloatAsState(
                if (!nightMode) 0.1f else 0.8f,
                animationSpec = tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing)
            )
            val bebFaceAlpha: Float by
            animateFloatAsState(
                if (!nightMode) 0.7f else 0.8f,
                animationSpec = tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing)
            )


            val backgroundColor by
            animateColorAsState(
                if (nightMode) nightTime else dayTime,
                animationSpec = tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing)
            )

            Canvas(modifier = Modifier.fillMaxSize()) { drawRect(backgroundColor, alpha = sunMoonAlpha) }

            Box(modifier = Modifier.align(Alignment.TopEnd).offset(x = 50.dp, y = (-50).dp)) {
                val sunAlpha: Float by
                animateFloatAsState(
                    if (!nightMode) 0.3f else 0.0f,
                    animationSpec =
                    tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing)
                )
                val moonAlpha: Float by
                animateFloatAsState(
                    if (!nightMode) 0.0f else 0.5f,
                    animationSpec =
                    tween(durationMillis = 3000, delayMillis = 0, easing = LinearEasing)
                )

                SimpleLottie(
                    lottieId = R.raw.sun1,
                    size = 200.dp,
                    modifier = Modifier.align(Alignment.Center).alpha(sunAlpha))
                SimpleLottie(
                    lottieId = R.raw.moon3,
                    size = 250.dp,
                    modifier = Modifier.align(Alignment.Center).alpha(moonAlpha))
                Image(
                    painter = bebface,
                    contentDescription = "BebFace",
                    modifier =
                    Modifier.align(Alignment.Center).alpha(bebFaceAlpha).size(70.dp).clickable {
                        toggleNightMode()
                    })
            }
        }
    }
}
