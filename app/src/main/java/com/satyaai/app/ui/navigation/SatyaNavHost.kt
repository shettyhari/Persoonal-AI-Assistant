package com.satyaai.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.satyaai.app.ui.screens.AutomationScreen
import com.satyaai.app.ui.screens.FileUploadScreen
import com.satyaai.app.ui.screens.GoogleSignInScreen
import com.satyaai.app.ui.screens.HomeChatScreen
import com.satyaai.app.ui.screens.IntegrationsScreen
import com.satyaai.app.ui.screens.NotesRemindersScreen
import com.satyaai.app.ui.screens.SettingsScreen
import com.satyaai.app.ui.screens.SplashScreen
import com.satyaai.app.ui.screens.VoiceAssistantScreen
import com.satyaai.app.viewmodel.MainViewModel

@Composable
fun SatyaNavHost() {
    val navController = rememberNavController()
    val vm: MainViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = NavRoutes.Splash) {
        composable(NavRoutes.Splash) { SplashScreen { navController.navigate(NavRoutes.SignIn) } }
        composable(NavRoutes.SignIn) { GoogleSignInScreen { navController.navigate(NavRoutes.Home) } }
        composable(NavRoutes.Home) { HomeChatScreen(vm, navController) }
        composable(NavRoutes.Voice) { VoiceAssistantScreen(vm) }
        composable(NavRoutes.Files) { FileUploadScreen(vm) }
        composable(NavRoutes.Integrations) { IntegrationsScreen() }
        composable(NavRoutes.Notes) { NotesRemindersScreen() }
        composable(NavRoutes.Automation) { AutomationScreen() }
        composable(NavRoutes.Settings) { SettingsScreen(vm) }
    }
}
