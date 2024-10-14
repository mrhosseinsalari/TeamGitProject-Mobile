package com.example.teamgitproj.ui.features

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.burnoo.cokoin.navigation.getNavController
import com.example.teamgitproj.ui.widgets.BlogToolbar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BlogScreenUi() {
    val navigation = getNavController()
    var showInfoDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BlogToolbar(
                titleArticle = "" ,
                onBackPressed = { navigation.popBackStack() } ,
                onInfoClicked = { showInfoDialog = true }
            )
        } ,
    ) {

    }
}