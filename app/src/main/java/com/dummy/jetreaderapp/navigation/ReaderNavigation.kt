package com.dummy.jetreaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dummy.jetreaderapp.screens.BookDetailsScreen
import com.dummy.jetreaderapp.screens.HomeScreen
import com.dummy.jetreaderapp.screens.LoginScreen
import com.dummy.jetreaderapp.screens.SearchScreen
import com.dummy.jetreaderapp.screens.SplashScreen
import com.dummy.jetreaderapp.screens.UserStatsScreen

@Composable
fun ReaderNavigation()
{
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination =Reader.SplashScreen.name ) {
        composable(Reader.SplashScreen.name){
            SplashScreen(navController)
        }
        composable(Reader.LoginScreen.name){
            LoginScreen(navController)
        }

        composable(Reader.HomeScreen.name){
            HomeScreen(navController)
        }

        composable(Reader.UserStatsScreen.name){
            UserStatsScreen(navController)
        }

        composable(Reader.BookDetailScreen.name){
            BookDetailsScreen(navController)
        }

        composable(Reader.SearchScreen.name){
            SearchScreen(navController)
        }
    }

}