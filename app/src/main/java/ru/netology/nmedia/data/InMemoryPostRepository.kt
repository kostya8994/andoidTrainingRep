package ru.netology.nmedia.data

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class InMemoryPostRepository {
    val data = MutableLiveData(
        List(10) { index ->
            Post(
                0,
                "0",
                "0",
                "Константин",
                "Контент $index",
                false,
                "09.08.2022",
                id = index + 1
            )
        }
    )

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    fun like(postId: Int) {
        data.value = posts.map {
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

    fun share(postId: Int) {
        data.value = posts.map {
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
}