package ru.netology.nmedia

data class Post(
    var likedByMy: Boolean = false,
    var numberLickes: String = "0",
    var numberShare: String = "0",
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 0,
    var share: Int = 0
)
