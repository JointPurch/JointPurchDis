package com.example.jointpurch.data

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    var id: String,
    var name: String,
    var users: MutableList<User>?,
    var logins: MutableList<String>?,
    var items: MutableList<Item>
)
