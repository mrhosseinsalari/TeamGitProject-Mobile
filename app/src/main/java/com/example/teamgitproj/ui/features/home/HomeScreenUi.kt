package com.example.teamgitproj.ui.features.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import com.example.teamgitproj.ui.theme.cBackground
import com.example.teamgitproj.ui.widgets.HomeContent
import com.example.teamgitproj.ui.widgets.HomeDrawer
import com.example.teamgitproj.ui.widgets.HomeToolbar
import com.example.teamgitproj.util.MyScreens
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenUi() {
    val activity = (LocalContext.current as? Activity)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val viewModel = getNavViewModel<HomeViewModel>()
    val navigation = getNavController()

    val data by viewModel.blogs.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HomeToolbar(
                onDrawerClicked = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
                onSearchClicked = {
                    navigation.navigate(MyScreens.SearchBlogScreen.route)
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
        drawerGesturesEnabled = true,
        drawerContent = {
            HomeDrawer {
                scope.launch {
                    if(scaffoldState.drawerState.currentValue == DrawerValue.Open) {
                        scaffoldState.drawerState.close()
                    } else {
                        activity?.finish()
                    }
                }
            }
        },
        drawerElevation = 2.dp,
        drawerBackgroundColor = cBackground
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            } else {
                HomeContent(data) {
                    viewModel.fetchBlogs()
                }

            }
        }
    }
}