package com.dummy.jetreaderapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dummy.jetreaderapp.component.InputField
import com.dummy.jetreaderapp.component.PasswordInputField

@Composable
fun LoginScreen(navController: NavController) {

    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }
    Surface(Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "A.Reader",

                style = MaterialTheme.typography.titleLarge, color = Color.Red.copy(alpha = 0.5f)
            )

            if (showLoginForm.value) UserForm(loading = false, isCreateAccount = false) { email, pwd ->
                Log.d("LoginForm", "LoginScreen: $email $pwd ")
            } else
            {
                UserForm(loading = false, isCreateAccount = true) { email, pwd ->
                    Log.d("LoginForm", "LoginScreen: $email $pwd ")
                }

            }
            
            LastItem(showLoginForm)



        }
    }
}

@Composable
fun LastItem(showLoginForm: MutableState<Boolean>) {
    Spacer(modifier = Modifier.height(5.dp))

    Row(modifier = Modifier.padding(15.dp), horizontalArrangement = Arrangement.Center,verticalAlignment = Alignment.CenterVertically)
    {
        val textId = if(showLoginForm.value) "Sign up" else "Login"

        Text(text = "New User?")
        Text(text = textId, modifier = Modifier
            .clickable {
                showLoginForm.value = !showLoginForm.value
            }
            .padding(start = 5.dp), color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val userEmail = rememberSaveable {
        mutableStateOf("")
    }

    val userPassword = rememberSaveable {
        mutableStateOf("")
    }

    val passwordVisibilty = rememberSaveable {
        mutableStateOf(false)
    }

    val passwordFocusRequest = FocusRequester.Default

    val keyboardController = LocalSoftwareKeyboardController.current

    val valid = remember(userEmail.value, userPassword.value) {
        userEmail.value.trim().isNotEmpty() && userPassword.value.trim().isNotEmpty()
    }


    Column(
        Modifier
            .size(400.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(
                rememberScrollState()
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        if (isCreateAccount)
        {
            Text(text = "Please Create Account with correct email and password with least 6 digit  long ", modifier = Modifier.padding(4.dp))
        }

        EmailInputField(
            modifier = Modifier,
            emailState = userEmail,
            enabled = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            })

        PasswordInputField(
            Modifier.focusRequester(passwordFocusRequest),
            passwordState = userPassword,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibilty,
            imeAction = androidx.compose.ui.text.input.ImeAction.Done,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(userEmail.value, userPassword.value)
            }
        )

        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ) {
            onDone(userEmail.value, userPassword.value)
            keyboardController?.hide()

        }


    }
}

@Composable
fun SubmitButton(textId: String, loading: Boolean, validInputs: Boolean, onClicked: () -> Unit) {

    Button(
        onClick = { onClicked.invoke() },
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,

    ) {
        if (loading)
        {
            CircularProgressIndicator(modifier = Modifier.size(25.dp))
        }else
        {
            Text(text = textId, modifier = Modifier.padding(5.dp))
        }
    }
}


@Composable
fun EmailInputField(
    modifier: Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: androidx.compose.ui.text.input.ImeAction = androidx.compose.ui.text.input.ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    InputField(
        modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )

}



    
