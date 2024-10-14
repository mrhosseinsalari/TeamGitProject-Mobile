package com.example.teamgitproj.ui.features

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.burnoo.cokoin.navigation.getNavController
import com.example.teamgitproj.data.model.Blog
import com.example.teamgitproj.ui.widgets.BlogToolbar
import com.example.teamgitproj.util.Cache
import com.example.teamgitproj.util.KEY_BLOG

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BlogScreenUi() {
    val navigation = getNavController()
    var showInfoDialog by remember { mutableStateOf(false) }
    val blog = Cache.get(KEY_BLOG)

    Scaffold(
        topBar = {
            BlogToolbar(
                titleArticle = blog.title ,
                onBackPressed = { navigation.popBackStack() } ,
                onInfoClicked = { showInfoDialog = true }
            )
        } ,
    ) {

    }
}