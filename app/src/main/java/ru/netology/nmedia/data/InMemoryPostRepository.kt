package ru.netology.nmedia.data

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class InMemoryPostRepository {
    private var likes: Int = 0
    private var share: Int = 0

    val data = MutableLiveData(
        Post(false, "999", "0", "Константин", "Контент", "09.08.2022")
    )

    fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        var likedPost = currentPost.copy(
            likedByMy = !currentPost.likedByMy
        )
        if (likedPost.likedByMy) likes++ else likes--
        likedPost = likedPost.copy(numberLickes = roundingNumbers(likes))
        data.value = likedPost
    }

    fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        share++
        val sharePost = currentPost.copy(
            numberShare = roundingNumbers(share)
        )
        data.value = sharePost
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