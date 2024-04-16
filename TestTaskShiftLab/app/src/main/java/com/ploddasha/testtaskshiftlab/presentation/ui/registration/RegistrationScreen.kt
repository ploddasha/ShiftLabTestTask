package com.ploddasha.testtaskshiftlab.presentation.ui.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.ploddasha.testtaskshiftlab.R

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = viewModel(
        factory = RegistrationViewModel.Factory
    ),
    onToMainScreenClick: () -> Unit,
){
    val registrationUiState by registrationViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        inputField(registrationViewModel, registrationUiState)

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                if (registrationViewModel.areAllFieldsValid()) {
                    if (registrationViewModel.signUp()) {
                        onToMainScreenClick()
                    }
                }
            },
            enabled = registrationViewModel.areAllFieldsValid()
        ) {
            Text(
                text = stringResource(R.string.registration),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun inputField(
    registrationViewModel: RegistrationViewModel,
    registrationUiState: RegistrationUiState,
){

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        if (registrationUiState.enteredPasswordWrong) {
            Text(
                text = stringResource(R.string.password_rule),
                color = MaterialTheme.colorScheme.error
            )
        }

        //Name
        OutlinedTextField(
            value = registrationViewModel.userInputName,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = {
                registrationViewModel.updateUserName(it)
                registrationViewModel.checkName()
            },
            label = {
                if (registrationUiState.enteredNameWrong) {
                    Text(stringResource(R.string.wrong_name))
                } else {
                    Text(stringResource(R.string.enter_name))
                }
            },
            isError = registrationUiState.enteredNameWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    registrationViewModel.checkName()
                }
            )
        )

        //Surname
        OutlinedTextField(
            value = registrationViewModel.userInputSurname,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = {
                registrationViewModel.updateUserSurname(it)
                registrationViewModel.checkSurname() },
            label = {
                if (registrationUiState.enteredSurnameWrong) {
                    Text(stringResource(R.string.wrong_surname))
                } else {
                    Text(stringResource(R.string.enter_surname))
                }
            },
            isError = registrationUiState.enteredSurnameWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { registrationViewModel.checkSurname() }
            )
        )

        //BirthdayDate
        OutlinedTextField(
            value = registrationViewModel.userInputBirthdayDate,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { registrationViewModel.updateUserBirthdayDate(it) },
            label = {
                if (registrationUiState.enteredBirthdayDateWrong) {
                    Text(stringResource(R.string.wrong_birthday_date))
                } else {
                    Text(stringResource(R.string.enter_birthday_date))
                }
            },
            isError = registrationUiState.enteredBirthdayDateWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.NumberPassword
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    registrationViewModel.checkBirthdayDate()
                }
            ),
            visualTransformation = DateTransformation()
        )

        //Password
        OutlinedTextField(
            value = registrationViewModel.userInputPassword,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = { registrationViewModel.updateUserPassword(it) },
            label = {
                if (registrationUiState.enteredPasswordWrong) {
                    Text(stringResource(R.string.wrong_password))
                } else {
                    Text(stringResource(R.string.enter_password))
                }
            },
            isError = registrationUiState.enteredPasswordWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { registrationViewModel.checkPassword() }
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        //Password confirmation
        OutlinedTextField(
            value = registrationViewModel.userInputConfirmationPassword,
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
            onValueChange = {
                registrationViewModel.updateUserConfirmationPassword(it)
                registrationViewModel.checkConfirmationPassword()
                            },
            label = {
                if (registrationUiState.enteredConfirmationPasswordWrong) {
                    Text(stringResource(R.string.wrong_confirmation_password))
                } else {
                    Text(stringResource(R.string.enter_confirmation_password))
                }
            },
            isError = registrationUiState.enteredConfirmationPasswordWrong,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { registrationViewModel.checkConfirmationPassword() }
            ),
            visualTransformation = PasswordVisualTransformation()

        )
    }
}

class DateTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }
}

fun dateFilter(text: AnnotatedString): TransformedText {

    val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 2 == 1 && i < 4) out += "."
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 3) return offset +1
            if (offset <= 8) return offset +2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <=2) return offset
            if (offset <=5) return offset -1
            if (offset <=10) return offset -2
            return 8
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}
