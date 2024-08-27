package com.dummy.jetreaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dummy.jetreaderapp.screens.LoginScreen
import com.dummy.jetreaderapp.screens.SplashScreen

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
    }

}