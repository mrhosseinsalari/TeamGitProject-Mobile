package com.example.teamgitproj.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import com.example.teamgitproj.di.myModules
import com.example.teamgitproj.ui.features.BlogScreenUi
import com.example.teamgitproj.ui.features.LargeImageScreen
import com.example.teamgitproj.ui.features.home.HomeScreenUi
import com.example.teamgitproj.ui.features.search.SearchScreenUi
import com.example.teamgitproj.ui.theme.TeamGitProjTheme
import com.example.teamgitproj.ui.theme.cBackground
import com.example.teamgitproj.util.MyScreens
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Koin(appDeclaration = {
                    androidContext(this@MainActivity)
                    modules(myModules)
                }) {
                    TeamGitProjTheme {
                        val uiController = rememberSystemUiController()
                        SideEffect {
                            uiController.setStatusBarColor(cBackground)
                        }

                        Surface(color = cBackground, modifier = Modifier.fillMaxSize()) {
                            TeamGitApp()
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun TeamGitApp() {
    val navController = rememberNavController()
    KoinNavHost(
        navController = navController,
        startDestination = MyScreens.HomeScreen.route
    ) {
        composable(MyScreens.HomeScreen.route) {
            HomeScreenUi()
        }

        composable(MyScreens.BlogScreen.route) {
            BlogScreenUi()
        }

        composable(MyScreens.LargeImageScreen.route) {
            LargeImageScreen()
        }

        composable(MyScreens.SearchBlogScreen.route) {
            SearchScreenUi()
        }
    }
}