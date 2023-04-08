package com.example.assignment2_v2

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun SearchNav(navController: NavHostController, snackbarHostState: SnackbarHostState){
    NavHost(
        navController = navController,
        startDestination = "search",
    ) {
        composable("search") { SearchScreen(navController) }
        composable("items/{searchId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("searchId").toString()
            Details(id,navController, snackbarHostState)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    var searchText = rememberSaveable { mutableStateOf("") }
    var filteredData = rememberSaveable { mutableStateOf(listOf<Feed>()) }
    var f5 by remember { mutableStateOf(1) }
    var tmp = rememberSaveable{ mutableStateOf("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx") } // default: empty
    var filterPage by remember { mutableStateOf(1) }

    val feeds = produceState( //lab9, default: empty
        initialValue = listOf<Feed>(),
        producer = {
            value = KtorClient.getKeyWords(tmp.value,filterPage.toString())
        },
        key1 = f5
    )

    Column {
        TextField(
            value = searchText.value,
            onValueChange = { searchText.value = it },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = { Text("Search") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    //
                }
            ),
            trailingIcon = {
                if (searchText.value.isNotEmpty()) {
                    IconButton(onClick = { searchText = mutableStateOf("") }) {
                        Icon(Icons.Filled.Clear, "Clear search")
                    }
                }
            }
        )
        Button(
            onClick = {
                tmp.value = searchText.value
                f5 += 1
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text("Search")
        }
        if(feeds.value.isNotEmpty()){
            LazyColumn(
                modifier = Modifier
                    .padding(top = 0.dp)
                    .height(450.dp)
                // hidden when no search result
            ) {
                filteredData.value = feeds.value.filter { feed ->
                    feed.title?.contains(tmp.value, ignoreCase = true) ?: false
                }
                items(feeds.value) { feed ->
                    androidx.compose.material3.ListItem(
                        headlineText = { Text(feed.title.toString()) },
                        modifier = Modifier.clickable {
                            navController.navigate("items/${feed._id}") //pass to event page
                            Log.d("ID:", feed._id.toString())
                        }
                    )
                    Divider()
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            androidx.compose.material3.Button(
                onClick = {
                    if (filterPage > 1) {
                        filterPage--
                        f5 += 1
                    }
                },
                modifier = Modifier.padding(end = 0.dp, start = 7.dp, top = 7.dp)
            ) {
                androidx.compose.material3.Text("Previous")
            }
            androidx.compose.material3.Text(
                filterPage.toString(),
                modifier = Modifier.padding(end = 0.dp, start = 0.dp, top = 20.dp)
            )
            androidx.compose.material3.Button(
                onClick = {
                    filterPage++
                    f5 += 1
                },
                modifier = Modifier.padding(end = 20.dp, start = 9.dp, top = 7.dp)
            ) {
                androidx.compose.material3.Text("Next")
            }
        }
    }
}