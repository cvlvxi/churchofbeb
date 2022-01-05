package com.cvlvxi.churchofbeb

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.cvlvxi.churchofbeb.areas.ChurchEntrance
import com.cvlvxi.churchofbeb.areas.InsideChurch

sealed class Areas(val area: String) {
    object ChurchEntrance: Areas("churchEntrance")
    object InsideChurch: Areas("insideChurch")
}

fun NavGraphBuilder.addChurchEntrance(
    nav: NavController,
) {
    composable(route = Areas.ChurchEntrance.area) {
        ChurchEntrance(navInsideHandle = {
            nav.navigate(Areas.InsideChurch.area)
        }, modifier = Modifier.fillMaxSize())
    }
}

fun NavGraphBuilder.addInsideChurch(
//    nav: NavController,
) {
    composable(route = Areas.InsideChurch.area) {
        InsideChurch()
    }
}

