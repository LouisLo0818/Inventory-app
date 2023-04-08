package com.example.assignment2_v2

import android.util.Log
import androidx.compose.material3.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

data class Items(val name: String, val id: String) {
    companion object {
        val data = listOf(
            Items("Books", "book"),
            Items("Games", "game"),
            Items("Gifts", "gift"),
            Items("Materials", "material")
        )
    }
}

@Composable
fun ItemsNav(navController: NavHostController, snackbarHostState: SnackbarHostState) {

    NavHost(
        navController = navController,
        startDestination = "items",
    ) {
        composable("items") { ItemsScreen(navController) }
        composable("items/{itemsId}/{page}") { backStackEntry ->
            //Log.d("Page:", backStackEntry.arguments?.getInt("page").toString())
            val feeds = produceState( //lab9, default: empty
                initialValue = listOf<Feed>(),
                producer = {
                    value = KtorClient.getFeeds(
                        backStackEntry.arguments?.getString("itemsId"),
                        backStackEntry.arguments?.getString("page"),
                    )
                }
            )
            Log.d( "FeedScreen: ", feeds.value.toString())
            val type = backStackEntry.arguments?.getString("itemsId")
            if (type != null) {
                FeedScreen(feeds.value, type, navController)
            }
        }
        composable("items/{itemsId}/{page}/{detailsId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("detailsId").toString()
            Details(id,navController, snackbarHostState)
        }
        composable("items/{detailsId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("detailsId").toString()
            Details(id,navController, snackbarHostState)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScreen(navController: NavHostController) {

    LazyColumn {
        items(Items.data) { Item ->
            ListItem(
                headlineText = { Text(Item.name) },
                modifier = Modifier.clickable {
                    navController.navigate("items/${Item.id}/${1}") //pass to event page
                },
                leadingContent = {
                    when (Item) {
                        Items("Books", "book") -> {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null
                            )
                        }
                        Items("Games", "game") -> {
                            Icon(
                                Icons.Filled.PlayArrow,
                                contentDescription = null
                            )
                        }
                        Items("Gifts", "gift") -> {
                            Icon(
                                Icons.Filled.ThumbUp,
                                contentDescription = null
                            )
                        }
                        Items("Materials", "material") -> {
                            Icon(
                                Icons.Filled.ShoppingCart,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
            Divider()
        }
    }
}
