package com.ploddasha.testtaskshiftlab.presentation.ui.greeting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ploddasha.testtaskshiftlab.ShiftApplication
import com.ploddasha.testtaskshiftlab.data.UserNameRepository
import kotlinx.coroutines.launch

class GreetingViewModel(
    private val userNameRepository: UserNameRepository
): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ShiftApplication)
                GreetingViewModel(application.userNameRepository)
            }
        }
    }

    var userName by mutableStateOf("")
        private set


    init {
        viewModelScope.launch {
            userNameRepository.userName.collect { name ->
                userName = name
            }
        }
    }
}