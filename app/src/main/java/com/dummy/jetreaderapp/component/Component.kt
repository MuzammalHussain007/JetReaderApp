package com.dummy.jetreaderapp.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dummy.jetreaderapp.data.MBook
import com.dummy.jetreaderapp.navigation.Reader
import com.google.firebase.auth.FirebaseAuth

@Composable
fun InputField(modifier: Modifier = Modifier,
               valueState : MutableState<String>,
               labelId : String,
               enabled : Boolean,
               isSingleLine : Boolean =true,
               keyboardType : androidx.compose.ui.text.input.KeyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
               imeAction : androidx.compose.ui.text.input.ImeAction = androidx.compose.ui.text.input.ImeAction.Next,
               onAction: KeyboardActions = KeyboardActions.Default) {


    OutlinedTextField(value = valueState.value, onValueChange = {
        valueState.value=it
    },label = {
        Text(text = labelId)
    }, singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp).fillMaxWidth(), enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction)
    )

}


@Composable
fun PasswordInputField(
    focusRequester: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction,
    onAction: KeyboardActions = KeyboardActions.Default
) {

  val  visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text("$labelId") },
        singleLine = true,
        enabled = enabled,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation, trailingIcon = {
            PasswordVisibility(passwordVisibility = passwordVisibility)
        }

    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    androidx.compose.material3.IconButton(onClick = {
        passwordVisibility.value = !visible
    }) {
        Icons.Default.Close
    }
}


@Composable
fun ReadNowArea(bookList: List<MBook>, navController: NavController) {

}

@Composable
fun TitleSection(modifier: Modifier = Modifier, label: String) {
    Surface(modifier = modifier.padding(start = 5.dp, top = 1.dp)) {

        Column {
            Text(
                text = label, style = TextStyle(
                    color = Color.Black.copy(alpha = 0.4f), fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,

                    fontSize = 19.sp
                )
            )
        }

    }
}

@Preview
@Composable
fun ReadingNowStatus(label: String = "Reading", onClick: () -> Unit = {}, radius: Int = 50) {

    Surface(
        color = Color(0xFF92CBDF),
        modifier =
        Modifier.clip(
            RoundedCornerShape(bottomEndPercent = radius,
            topStartPercent = radius)
        ),) {
        Column(modifier = Modifier
            .width(90.dp)
            .heightIn(50.dp)
            .clickable { onClick.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, color = Color.White)

        }
    }

}

@Composable
fun BookAuthor(title: String) {

    Text(
        text = "$title",
        modifier = Modifier.padding(start = 5.dp),
        style = MaterialTheme.typography.titleSmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun BookTitle(title: String = "Compose") {

    Text(
        text = title,
        modifier = Modifier.padding(start = 4.dp, top = 4.dp, end = 4.dp, bottom = 0.dp),
        style = MaterialTheme.typography.titleMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )

}

@Composable
fun BookRating(score: Double) {
    Surface(
        shape = RoundedCornerShape(56.dp),
        modifier = Modifier
            .padding(4.dp)
            .height(80.dp),
        shadowElevation = 10.dp,
        color = Color.White
    )
    {
        Column(Modifier.padding(4.dp)) {

            Icon(
                imageVector = Icons.Filled.StarRate,
                contentDescription = "",
                modifier = Modifier.padding(3.dp)
            )
            Text(
                text = score.toString(),
                modifier = Modifier.padding(top = 5.dp, start = 4.dp),
                style = MaterialTheme.typography.titleMedium
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileToolBar(
    title: String,
    showProfile: Boolean,
    icon:ImageVector?=null,
    onIconClicked : ()->Unit={},
    navController: NavController
) {
    TopAppBar(
        title = {
            MakeTitle(title = title, showProfile = showProfile,icon){
                if (icon!=null){
                    onIconClicked.invoke()
                }
            }
        },
        actions = {
            Actions(navController,showProfile) {}
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),


        )
}


@Composable
fun MakeTitle(title: String, showProfile: Boolean,icon:ImageVector?=null,onIconClicked : ()->Unit={}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        if (showProfile) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .scale(0.9f)

            )
        }



        if (icon!=null)
        {
            Icon(imageVector = icon!!, contentDescription = "", modifier = Modifier.clickable { onIconClicked.invoke() }.padding(end = 10.dp), tint = Color.Red.copy(0.7f))

        }



        Text(
            text = title,
            color = Color.Red.copy(0.4f),
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.weight(150f))


    }
}

@Composable
fun Actions(navController: NavController,showProfile: Boolean, onActionDone: () -> Unit) {

    IconButton(onClick = {
        FirebaseAuth.getInstance().signOut().run {
            navController.navigate(Reader.LoginScreen.name) {
                popUpTo(Reader.HomeScreen.name) {
                    inclusive = true
                }
            }
        }
    }) {
        if (showProfile)
        {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = "",
                tint = Color.Green.copy(alpha = 0.4f)
            )
        }

    }

}