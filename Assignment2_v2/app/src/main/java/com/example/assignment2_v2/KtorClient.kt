package com.example.assignment2_v2

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class HttpBinResponse(
    val first_name: String?,
    val last_name: String?,
    val token: String?,
    var error: String?
)

var logined : Boolean = false

object KtorClient {
    private var token: String = ""
    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            }) // enable the client to perform JSON serialization
        }
        install(Logging)
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header("Authorization", token)
        }
        expectSuccess = true
    }

    suspend fun getFeeds(type: String?, page: String?): List<Feed> {
        return httpClient.get("https://comp4107.azurewebsites.net/inventory?type=${type}&page=${page}")
            .body()  //conversion: text --?
    }

    suspend fun getFeedsDetails(id: String?): Feed {
        return httpClient.get("http://comp4107.azurewebsites.net/inventory/${id}")
            .body()  //conversion: text --?
    }

    suspend fun getKeyWords(words: String?, page : String?): List<Feed>{
        return httpClient.get("http://comp4107.azurewebsites.net/inventory?keyword=${words}&page=${page}")
            .body()  //conversion: text --?
    }

    suspend fun postFeedback(testing: loginResponse): String {
        var message = ""
        var response: HttpBinResponse? = null
        try {
            response = httpClient.post("https://comp4107.azurewebsites.net/user/login") {
                setBody(testing)
            }.body()
            message = "Login Successful"
            token = response?.token.toString()
            logined = true
        } catch (e: Exception) {
            message = "Login Failed"
        }


        //token = response.token.toString()
        return message
    }

    suspend fun borrowBook(itemID: String?): String? {
        var message = ""
        var response: HttpBinResponse? = null
        try {
            response = httpClient.post("https://comp4107.azurewebsites.net/user/borrow/${itemID}") {
                //setBody(itemID)
            }.body()
            message = "borrow Successful"
        } catch (e: Exception) {
            message = "borrow Failed"
            Log.d("Error", e.toString())
        }
        //token = response.token.toString()
        return message
    }

    suspend fun returnBook(itemID: String?): String? {
        var message = ""
        var response: HttpBinResponse? = null
        try {
            response = httpClient.post("http://comp4107.azurewebsites.net/user/return/${itemID}") {
                //setBody(itemID)
            }.body()
            message = "Return Successful"
        } catch (e: Exception) {
            message = "Return Failed"
            Log.d("Error", e.toString())
        }
        //token = response.token.toString()
        return message
    }

    suspend fun consumeItem(itemID: String?): String? {
        var message = ""
        var response: HttpBinResponse? = null
        try {
            response = httpClient.post("http://comp4107.azurewebsites.net/user/consume/${itemID}") {
                //setBody(itemID)
            }.body()
            message = "Consume Successful"
        } catch (e: Exception) {
            message = "Consume Failed"
            Log.d("Error", e.toString())
        }
        //token = response.token.toString()
        return message
    }
}
