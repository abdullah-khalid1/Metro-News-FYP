package com.mynews.metronews.model

data class Post(
    val email: String = "",
    val title: String = "",
    val description: String = "",
    val author_name: String = "",
    val id: String = "",
    var downloadUrlImage: String = "",
    var date: String = ""
)