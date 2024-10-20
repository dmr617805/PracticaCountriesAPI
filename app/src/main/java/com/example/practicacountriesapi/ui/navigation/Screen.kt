package com.example.practicacountriesapi.ui.navigation

sealed class Screen(val route: String) {
    data object Main: Screen("main")
    data object Detail: Screen("detail")
}