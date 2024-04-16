package com.ploddasha.testtaskshiftlab.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ploddasha.testtaskshiftlab.R
import com.ploddasha.testtaskshiftlab.presentation.ui.greeting.GreetingScreen
import com.ploddasha.testtaskshiftlab.presentation.ui.greeting.GreetingViewModel
import com.ploddasha.testtaskshiftlab.presentation.ui.registration.RegistrationScreen

enum class ShiftScreen(@StringRes val title: Int) {
    Registration(title = R.string.registration),
    Greeting(title = R.string.greeting),
}

@Composable
fun ShiftApp(
    navController: NavHostController = rememberNavController(),
    greetingViewModel: GreetingViewModel = viewModel(factory = GreetingViewModel.Factory)
) {
    val userName = greetingViewModel.userName
    val startDestination = if (userName.isEmpty()) {
        ShiftScreen.Registration.name
    } else {
        ShiftScreen.Greeting.name
    }

    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ShiftScreen.Registration.name) {
                RegistrationScreen(
                    onToMainScreenClick = {
                        navController.navigate(ShiftScreen.Greeting.name)
                    },
                )
            }
            composable(route = ShiftScreen.Greeting.name) {
                GreetingScreen(
                    greetingViewModel
                )
            }
        }
    }

}