package com.dummy.jetreaderapp.navigation

enum class Reader {
    SplashScreen,
    LoginScreen, SignUpScreen, BookDetailScreen, HomeScreen, SearchScreen, UpdateScreen, ReaderStatsScreen;

    companion object {
        fun fromRouteName(name: String): Reader = when (name.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            SignUpScreen.name -> SignUpScreen
            BookDetailScreen.name -> BookDetailScreen
            HomeScreen.name -> HomeScreen
            SearchScreen.name -> SearchScreen
            UpdateScreen.name -> UpdateScreen
            ReaderStatsScreen.name -> ReaderStatsScreen
            null -> HomeScreen
            else -> {
                throw java.lang.IllegalArgumentException("Route not found")
            }
        }
    }

}