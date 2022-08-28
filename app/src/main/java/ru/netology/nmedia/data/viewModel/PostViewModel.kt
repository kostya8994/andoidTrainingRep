package ru.netology.nmedia.data.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.InMemoryPostRepository

class PostViewModel : ViewModel() {
    private val repository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClick(post: Post) = repository.like(post.id)
    fun onShareClick(post: Post) = repository.share(post.id)
}