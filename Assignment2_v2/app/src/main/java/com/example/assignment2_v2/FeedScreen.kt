package com.example.assignment2_v2

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import kotlin.math.max
import kotlin.math.min

@Serializable
data class Feed(
    val _id: String?,
    val title: String?,
    val author: String? = null,
    val year: String? = null,
    val isbn: String? = null, //skip deserialization
    val image: String?,
    val quantity: Int? = null,
    val donatedBy: String? = null,
    val description: String?,
    val category: String? = null,
    val amount: Int? = null,
    val unitPrice: Int? = null,
    val publisher: String? = null,
    val location: String?,
    val remark: String?,
    val type: String?,
    val borrower: String? = null,
    val remaining: Int? = null
)

var page = 1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(feeds: List<Feed>, type: String, navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(type.capitalize()) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("items")
                        page = 1
                    }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.padding(top = 50.dp)
            ) {
                items(feeds) { feed ->
                    ListItem(
                        headlineText = { Text(feed.title.toString()) },
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        Log.d("testing", "onPress")
                                        navController.navigate("items/${type}/${page}/${feed._id}")
                                        Log.d("testing", feed._id.toString())
                                    }
                                )
                            }
                            .height(57.dp)
                    )
                    Divider()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if (page > 1) {
                            page--
                            Log.d("Page:", page.toString())
                            navController.navigate("items/${type}/${page}")
                        }
                    },
                    modifier = Modifier.padding(end = 0.dp, start = 7.dp, top = 0.dp)
                ) {
                    Text("Previous")
                }
                Text("$page", modifier = Modifier.padding(end = 0.dp, start = 0.dp, top = 10.dp))
                Button(
                    onClick = {
                        page++
                        Log.d("Page:", page.toString())
                        navController.navigate("items/${type}/${page}")
                    },
                    modifier = Modifier.padding(end = 20.dp, start = 9.dp, top = 0.dp)
                ) {
                    Text("Next")
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun FeedPreview() {
//    InfoDayTheme {
//        FeedScreen(Feed.data)
//    }
//}
