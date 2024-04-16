package com.ploddasha.testtaskshiftlab.presentation.ui.registration

data class RegistrationUiState(
    val name: String = "",
    val surname: String = "",
    val birthdayDate: String = "",
    val password: String = "",
    val confirmationPassword: String = "",

    val enteredNameWrong: Boolean = false,
    val enteredSurnameWrong: Boolean = false,
    val enteredBirthdayDateWrong: Boolean = false,
    val enteredPasswordWrong: Boolean = false,
    val enteredConfirmationPasswordWrong: Boolean = false,
)