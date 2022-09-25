package ru.netology.nmedia.data

import android.app.Application
import android.content.Context
import android.provider.Settings.Global.putInt
import android.provider.Settings.Global.putString
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import ru.netology.nmedia.Post
import kotlin.properties.Delegates

class SharedPrefsPostRepository(application: Application) : PostRepositiry {

    private val prefs = application.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private var nextId: Int by Delegates.observable(
        prefs.getInt(NEXT_TO_PRESS_KEY_ID, 0)
    ){_,_, newValue ->
        prefs.edit {putInt(NEXT_TO_PRESS_KEY_ID, newValue)}
    }

    override val data: MutableLiveData<List<Post>>

    init {
        val serializedPost = prefs.getString(POST_PREFS_KEY, null)
        val posts: List<Post> = if(serializedPost != null) {
            Json.decodeFromString(serializedPost)
        } else emptyList()
        data = MutableLiveData(posts)
    }

    private var posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value){
            prefs.edit {
                val serializetPost = Json.encodeToString(value)
                putString(POST_PREFS_KEY, serializetPost)
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
        const val POST_PREFS_KEY = "posts"
        const val NEXT_TO_PRESS_KEY_ID = "posts"
    }
}