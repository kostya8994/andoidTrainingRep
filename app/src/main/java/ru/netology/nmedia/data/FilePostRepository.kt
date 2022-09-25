package ru.netology.nmedia.data

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post
import kotlin.properties.Delegates

class FilePostRepository(private val application: Application) : PostRepositiry {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val prefs = application.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private var nextId: Int by Delegates.observable(
        prefs.getInt(NEXT_TO_PRESS_KEY_ID, 0)
    ) { _, _, newValue ->
        prefs.edit { putInt(NEXT_TO_PRESS_KEY_ID, newValue) }
    }

    override val data: MutableLiveData<List<Post>>

    init {
        val postsFile = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if (postsFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use { gson.fromJson(it, type) }
        } else emptyList()
        data = MutableLiveData(posts)
    }

    private var posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value) {
            application.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }

    override fun like(postId: Int) {
        posts = posts.map {
            if (it.id != postId) it
            else {
                if (it.likedByMy) {
                    it.copy(
                        likes = it.likes + 1,
                        numberLickes = roundingNumbers(it.likes + 1),
                        likedByMy = !it.likedByMy
                    )
                } else {
                    it.copy(
                        likes = it.likes - 1,
                        numberLickes = roundingNumbers(it.likes + 1),
                        likedByMy = !it.likedByMy
                    )
                }
            }
        }
    }

    override fun share(postId: Int) {
        posts = posts.map {
            if (it.id != postId) it
            else {
                it.copy(share = it.share++)
                it.copy(numberShare = roundingNumbers(it.share))
            }
        }
    }

    fun roundingNumbers(numbers: Int): String {
        if (numbers < 1000) {
            return numbers.toString()
        } else if (numbers >= 1000 && numbers <= 10000) {
            val numbersDouble: Double = (numbers / 100).toDouble() / 10
            return numbersDouble.toString() + "K"
        } else if (numbers >= 10000 && numbers < 1000000) {
            val numbersDouble: Int = numbers / 1000
            return numbersDouble.toString() + "K"
        } else {
            val numbersDouble: Double = numbers.toDouble() / 1000000
            return numbersDouble.toString() + "M"
        }
    }

    override fun delete(postId: Int) {
        posts = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {
        if (post.id == PostRepositiry.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(
                id = ++nextId
            )
        ) + posts
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }


    companion object {
        const val NEXT_TO_PRESS_KEY_ID = "posts"
        const val FILE_NAME = "posts.json"
    }
}