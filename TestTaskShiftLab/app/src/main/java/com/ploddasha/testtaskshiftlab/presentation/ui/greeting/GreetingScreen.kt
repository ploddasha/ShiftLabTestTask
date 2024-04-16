package com.ploddasha.testtaskshiftlab.presentation.ui.greeting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ploddasha.testtaskshiftlab.R

@Composable
fun GreetingScreen(
    greetingViewModel: GreetingViewModel
){

    var showDialog by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                showDialog = true
            }
        ) {
            Text(
                text = stringResource(R.string.greeting),
                fontSize = 16.sp
            )
        }

        if (showDialog) {
            AlertDialog(
                text = {
                    Text(text = stringResource(R.string.hi) + ", ${greetingViewModel.userName}!")
                },
                onDismissRequest = {
                    showDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(text = stringResource(R.string.hi_eq_mark))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(text = stringResource(R.string.close))
                    }
                }
            )
        }
    }
}