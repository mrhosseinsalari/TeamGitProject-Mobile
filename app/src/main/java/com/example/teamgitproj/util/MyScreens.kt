package com.example.teamgitproj.util

sealed class MyScreens(val route: String) {
    object HomeScreen : MyScreens("homeScreen")
    object BlogScreen : MyScreens("blogScreen")
    object LargeImageScreen : MyScreens("LargeImageScreen")
}