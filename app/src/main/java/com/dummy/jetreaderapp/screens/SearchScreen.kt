package com.dummy.jetreaderapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dummy.jetreaderapp.component.InputField
import com.dummy.jetreaderapp.component.ProfileToolBar
import com.dummy.jetreaderapp.data.MBook

@Preview(showBackground = true)
@Composable
fun SearchScreen(navController: NavController = NavController(LocalContext.current)) {

    Scaffold(topBar = {
        ProfileToolBar(
            title = "Search", showProfile = false,
            icon = Icons.Default.ArrowBack,
            onIconClicked = { navController.popBackStack() },
            navController = navController
        )
    }) { innerPadding ->

        Surface(modifier = Modifier.padding(innerPadding), color = Color.White) {
            Column {

                BookSearchForm(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    loading = false,
                    hint = "Search",
                    onSearch = { it->
                        MBook().searchBookByTitle(it)
                    }

                )

                BookListArea(booklist = MBook().searchBookByTitle("Android"), navController = navController )


            }
        }

    }
}


@Composable
fun BookSearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {

    Column {
        val searchQuery = rememberSaveable {
            mutableStateOf("")
        }

        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = rememberSaveable(searchQuery.value) {
            searchQuery.value.trim().isNotEmpty()
        }

        InputField(valueState = searchQuery, labelId = "Search", enabled = true , onAction = KeyboardActions{
            if (!valid) return@KeyboardActions
            onSearch(searchQuery.value.trim())
            searchQuery.value = ""
            keyboardController?.hide()
        })

    }

}