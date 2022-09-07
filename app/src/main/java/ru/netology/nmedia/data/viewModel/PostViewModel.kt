package ru.netology.nmedia.data.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.InMemoryPostRepository
import ru.netology.nmedia.data.PostRepositiry

class PostViewModel() : ViewModel() {
    private val repository = InMemoryPostRepository()

    val currentPost = MutableLiveData<Post?>(null)

    val data by repository::data

    fun onLikeClick(post: Post) = repository.like(post.id)
    fun onShareClick(post: Post) = repository.share(post.id)
    fun onDeleteClick(post: Post) = repository.delete(post.id)
    fun onEditClick(post: Post) {
        currentPost.value = post
    }

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val newPost = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepositiry.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "06.09.22"
        )
        repository.save(newPost)
        currentPost.value = null
    }
}