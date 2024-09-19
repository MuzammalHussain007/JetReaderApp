package com.dummy.jetreaderapp.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.dummy.jetreaderapp.component.BookAuthor
import com.dummy.jetreaderapp.component.BookRating
import com.dummy.jetreaderapp.component.BookTitle
import com.dummy.jetreaderapp.component.ProfileToolBar
import com.dummy.jetreaderapp.component.ReadingNowStatus
import com.dummy.jetreaderapp.component.TitleSection
import com.dummy.jetreaderapp.data.MBook
import com.dummy.jetreaderapp.navigation.Reader
import com.google.firebase.auth.FirebaseAuth

@Composable
fun FABContent(navController: NavController, onTap: (String) -> Unit) {

    FloatingActionButton(
        onClick = {
            navController.navigate(Reader.SearchScreen.name)
            onTap.invoke("") },
        shape = RoundedCornerShape(50.dp),
        containerColor = Color(0xFF92CBDF),

        ) {

        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSecondary
        )

    }

}

/*@Preview*/
@Composable
fun HomeScreen(navController: NavController /*= NavController(LocalContext.current)*/) {
    Scaffold(
        topBar = {
            ProfileToolBar(title = "A.Reader", showProfile = true, navController = navController)
        },
        floatingActionButton = {
            FABContent(navController = navController) {}
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HomeContent(navController = navController)
        }
    }
}






@Composable
fun HomeContent(navController: NavController) {

    var displayName = if (!FirebaseAuth.getInstance().currentUser?.displayName.isNullOrEmpty()) {
        "${FirebaseAuth.getInstance().currentUser?.displayName}"
    } else {
        "N/A"
    }

    Column(Modifier.padding(2.dp), verticalArrangement = Arrangement.Top) {

        Row(modifier = Modifier.align(Alignment.Start)) {
            TitleSection(label = "Your Reading \n activity right now...")
            Spacer(modifier = Modifier.fillMaxWidth(0.85f))
            Column {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            navController.navigate(Reader.UserStatsScreen.name)
                        },
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    text = displayName,
                    modifier = Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    fontSize = 12.sp,
                    maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Divider()
            }

        }

        CardList(MBook().getSingleBook())

        TitleSection(label = "Reading List")

        BookListArea(navController = navController, MBook().getBooks())
        






    }

}

@Composable
fun BookListArea(navController: NavController, booklist: List<MBook>) {

    HorizentalScrollArea(booklist,navController)

}

@Composable
fun HorizentalScrollArea(booklist: List<MBook> = emptyList(),navController: NavController) {

    LazyRow {
        items(booklist.size) {
            CardList(booklist[it]){
               // navController.navigate(Reader.BookDetailScreen.name)
            }
        }
    }

}


@Preview
@Composable
fun CardList(book: MBook = MBook(), onPresenting: (String) -> Unit = {}) {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics
    val screensWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = Modifier
            .padding(16.dp)
            .width(210.dp)
            .height(242.dp)
            .clickable { onPresenting.invoke(book.title.toString()) }
    ) {

        Column(
            Modifier.width(screensWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start,
        ) {

            Row(horizontalArrangement = Arrangement.Center) {
                val imageUrl = book.thambnail.toString()

                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .height(140.dp)
                        .width(100.dp)
                )

                Spacer(modifier = Modifier.width(50.dp))

                Column(
                    modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "",
                        modifier = Modifier.padding(bottom = 1.dp)
                    )

                    BookRating(score = 4.5)

                }

            }

            BookTitle(title = book.title.toString())

            BookAuthor(title = book.authors.toString())

            Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)) {
                ReadingNowStatus("Reading...", radius = 70)
            }

        }

    }
}







