package com.example.jointpurch

import android.content.Context
import com.example.jointpurch.data.Room
import com.example.jointpurch.data.User
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.security.MessageDigest
import java.util.UUID

const val SERVER_ADDRESS = "http://jointpurch.ddns.net:8000/"
//const val SERVER_ADDRESS = "http://192.168.0.12:8000/"

class RestApi(context: Context) {
    private var client: HttpClient

    init {
        val user: User? = SharedPreferenceManager.getUser(context)

        if (user != null) {
            client = HttpClient(CIO) {
                install(Auth) {
                    basic {
                        credentials {
                            BasicAuthCredentials(username = user.login, password = user.passwordHash!!)
                        }
                        realm = "Access to the '/' path"
                    }
                }
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
        }else {
            client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
        }
    }

    fun register(name: String, password: String): Int {
        val passwordHash: String = MessageDigest.getInstance("SHA-512")
            .digest(password.toByteArray())
            .joinToString(separator = "") {
                ((it.toInt() and 0xff) + 0x100)
                    .toString(16)
                    .substring(1)
            }

        val newUser = User(
            name,
            passwordHash
        )

        return runBlocking {
            val response = client.post(SERVER_ADDRESS + "user/register") {
                contentType(ContentType.Application.Json)
                setBody(newUser)
            }
//            println("STATUS CODE: ${response.status.value}")
            if (response.status.value == 400){
                throw Exception("400: Bad Request")
            }
            if (response.status.value == 401){
                throw Exception("401: Unauthorized")
            }
            response.status.value
        }
    }

    fun getMyRooms(): List<Room> {
        return runBlocking {
            val response = client.get(SERVER_ADDRESS + "room/my") {
                contentType(ContentType.Application.Json)
            }
            response.body<List<Room>>()
        }
    }

    fun login(user: User): Boolean {
        val passwordHash: String = MessageDigest.getInstance("SHA-512")
            .digest(user.passwordHash!!.toByteArray())
            .joinToString(separator = "") {
                ((it.toInt() and 0xff) + 0x100)
                    .toString(16)
                    .substring(1)
            }

        println("""AUTH
            |${user.login}
            |${passwordHash}
        """.trimMargin())
        val testClient = HttpClient(CIO) {
            install(Auth) {
                basic {
                    credentials {
                        BasicAuthCredentials(username = user.login, password = passwordHash)
                    }
                    realm = "Access to the '/' path"
                }
            }
        }

        val statusCode = runBlocking {
            val response = testClient.get(SERVER_ADDRESS + "room/my") {
                contentType(ContentType.Application.Json)
            }
            response.status.value
        }

        return statusCode == 200
    }

    fun createRoom(name: String, logins: List<String>): Int{
        val newRoom = Room(
            UUID.randomUUID().toString(),
            name,
            null,
            logins.toMutableList(),
            mutableListOf()
        )

        return runBlocking {
            val response = client.post(SERVER_ADDRESS + "room/register") {
                contentType(ContentType.Application.Json)
                setBody(newRoom)
            }
//            println("STATUS CODE: ${response.status.value}")
            if (response.status.value == 400){
                throw Exception("400: Bad Request")
            }
            if (response.status.value == 401){
                throw Exception("401: Unauthorized")
            }
            response.status.value
        }
    }
}