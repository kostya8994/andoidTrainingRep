package ru.netology.nmedia.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.FilePostRepository
import ru.netology.nmedia.data.PostRepositiry

class PostViewModel(application: Application): AndroidViewModel(application) {
    private val repository = FilePostRepository(application)

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

    fun onOpenVideoClicked(post: Post) {
        currentPost.value = post
    }
}