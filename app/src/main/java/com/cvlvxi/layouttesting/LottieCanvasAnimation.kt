package com.cvlvxi.layouttesting

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

/**
 * Pure Canvas Implementation
 *
 * Without Layout
 */
@Composable
fun DrawCanvas(modifier: Modifier) {
    val e = painterResource(R.drawable.entrance)
    Canvas(modifier=modifier, onDraw = {
        drawRect(Color.Blue, topLeft = Offset(0f, 0f))
    })
}