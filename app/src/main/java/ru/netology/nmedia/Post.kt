package ru.netology.nmedia

data class Post(
    var likes: Int = 0,
    var numberLickes: String = "0",
    var numberShare: String = "0",
    val author: String,
    var content: String = "Контент",
    var likedByMy: Boolean = false,
    val published: String,
    var share: Int = 0,
    val id: Int = 0
)
