package com.example.teamgitproj.util

sealed class MyScreens(val route: String) {
    object HomeScreen : MyScreens("homeScreen")
}