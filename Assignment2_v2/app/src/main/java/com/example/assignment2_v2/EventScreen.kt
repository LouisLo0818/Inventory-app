package com.example.assignment2_v2

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(id: String?, navController: NavController, snackbarHostState: androidx.compose.material3.SnackbarHostState) {
    var f5 by remember { mutableStateOf(1) }
    val coroutineScope = rememberCoroutineScope()
    val feeds = produceState(
        initialValue = Feed(
            _id = "",
            title = "",
            author = "",
            year = "",
            isbn = "",
            image = "",
            quantity = 0,
            donatedBy = "",
            description = "",
            category = "",
            amount = 0,
            unitPrice = 0,
            publisher = "",
            location = "",
            remark = "",
            type = "",
            borrower = "",
            remaining = 0
        ),
        producer = {
            value = KtorClient.getFeedsDetails(id)
        },
        key1 = f5
    )

    Log.d("Value", feeds.value.toString())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = feeds.value.image,
                    contentDescription = "Image",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                )
                when(feeds.value.type){
                    "book","game" -> {
                        Text(
                            text = "ID: " + feeds.value._id.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Title: " + feeds.value.title.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Author: " + feeds.value.author.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Year: " + feeds.value.year.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "ISBN: " + feeds.value.isbn.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Description: " + feeds.value.description.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Category: " + feeds.value.category.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "publisher: " + feeds.value.publisher.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Location: " + feeds.value.location.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Remark: " + feeds.value.remark.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Borrower: " + feeds.value.borrower.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                    } else -> {
                        Text(
                            text = "ID: " + feeds.value._id.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Title: " + feeds.value.title.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Description: " + feeds.value.description.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Category: " + feeds.value.category.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Amount: " + feeds.value.amount.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Unit Price: " + feeds.value.unitPrice.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Location: " + feeds.value.location.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Remark: " + feeds.value.remark.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                        Text(
                            text = "Remaining: " + feeds.value.remaining.toString(),
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                        )
                    }
                }
                when(feeds.value.type){
                    "book", "game" -> {
                        if (logined && feeds.value.borrower == "none") {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        val stringBody: String? = KtorClient.borrowBook(feeds.value._id)
                                        Log.d("BookID: ", feeds.value._id.toString())
                                        if (stringBody != null) {
                                            snackbarHostState.showSnackbar(stringBody)
                                        }
                                        f5 += 1
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 16.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text("Borrow")
                            }
                        } else if(feeds.value.borrower == "me") {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        val stringBody: String? = KtorClient.returnBook(feeds.value._id)
                                        Log.d("BookID: ", feeds.value._id.toString())
                                        if (stringBody != null) {
                                            snackbarHostState.showSnackbar(stringBody)
                                        }
                                        f5 += 1
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 16.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text("Return")
                            }
                        }
                    }
                    else -> {
                        if (logined && feeds.value.remaining!! > 0) {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        val stringBody: String? = KtorClient.consumeItem(feeds.value._id)
                                        Log.d("ItemID: ", feeds.value._id.toString())
                                        if (stringBody != null) {
                                            snackbarHostState.showSnackbar(stringBody)
                                        }
                                        f5 += 1
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 16.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text("Consume")
                            }
                        }
                    }
                }
            }
        },
    )
}
