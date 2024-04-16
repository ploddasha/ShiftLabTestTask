package com.ploddasha.testtaskshiftlab.presentation.ui.registration

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ploddasha.testtaskshiftlab.ShiftApplication
import com.ploddasha.testtaskshiftlab.data.UserNameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class RegistrationViewModel(
    private val userNameRepository: UserNameRepository
): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ShiftApplication)
                RegistrationViewModel(application.userNameRepository)
            }
        }
    }

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()


    var userInputName by mutableStateOf("")
        private set

    var userInputSurname by mutableStateOf("")
        private set

    var userInputBirthdayDate by mutableStateOf("")
        private set

    var userInputPassword by mutableStateOf("")
        private set

    var userInputConfirmationPassword by mutableStateOf("")
        private set



    fun updateUserName(enteredName: String){
        userInputName = enteredName
    }

    fun updateUserSurname(enteredSurname: String){
        userInputSurname = enteredSurname
    }

    fun updateUserBirthdayDate(enteredBirthdayDate: String){
        userInputBirthdayDate = enteredBirthdayDate
    }

    fun updateUserPassword(enteredPassword: String){
        userInputPassword = enteredPassword
    }

    fun updateUserConfirmationPassword(enteredConfirmationPassword: String){
        userInputConfirmationPassword = enteredConfirmationPassword
    }




    fun checkName() {
        if (userInputName.length < 2) {
            _uiState.update { currentState ->
                currentState.copy(enteredNameWrong = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredNameWrong = false)
            }
        }
    }

    fun checkSurname() {
        if (userInputSurname.length < 2) {
            _uiState.update { currentState ->
                currentState.copy(enteredSurnameWrong = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredSurnameWrong = false)
            }
        }
    }

    fun checkBirthdayDate() {
        val dateRegex = Regex("[0-9]{2}[.][0-9]{2}[.][0-9]{4}")

        if (userInputBirthdayDate.matches(dateRegex)) {
            _uiState.update { currentState ->
                currentState.copy(enteredBirthdayDateWrong = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredBirthdayDateWrong = false)
            }
        }
    }

    fun checkPassword() {
        if (userInputPassword.length > 5 &&
            userInputPassword.any{ it.isDigit() } &&
            userInputPassword.any { it.isUpperCase() } &&
            userInputPassword.none { it.isWhitespace() } &&
            userInputPassword.none { !it.isLetterOrDigit() }
            ) {
            _uiState.update { currentState ->
                currentState.copy(enteredPasswordWrong = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredPasswordWrong = true)
            }
        }
    }

    fun checkConfirmationPassword() {
        if (userInputConfirmationPassword != userInputPassword) {
            _uiState.update { currentState ->
                currentState.copy(enteredConfirmationPasswordWrong = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(enteredConfirmationPasswordWrong = false)
            }
        }
    }


    fun areAllFieldsValid(): Boolean {
        return !uiState.value.enteredNameWrong &&
                !uiState.value.enteredSurnameWrong &&
                !uiState.value.enteredBirthdayDateWrong &&
                !uiState.value.enteredPasswordWrong &&
                !uiState.value.enteredConfirmationPasswordWrong &&
                userInputName.isNotEmpty() &&
                userInputSurname.isNotEmpty() &&
                userInputBirthdayDate.isNotEmpty() &&
                userInputConfirmationPassword.isNotEmpty()
    }

    fun signUp(): Boolean {
        checkName()
        checkSurname()
        checkBirthdayDate()
        checkConfirmationPassword()
        if (areAllFieldsValid()) {
            viewModelScope.launch {
                userNameRepository.saveUserName(userInputName)
                Log.w("RegistrationViewModel", "User name saved successfully: $userInputName")
            }
            return true
        }
        return false
    }
}