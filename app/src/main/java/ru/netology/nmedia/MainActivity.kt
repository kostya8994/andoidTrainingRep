package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.data.viewModel.PostViewModel
import ru.netology.nmedia.databinding.PostBinding

class MainActivity : AppCompatActivity() {
    private val viewModel = PostViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.licksButton.setOnClickListener {
            viewModel.onLikeClick()
        }

        binding.shareButton.setOnClickListener {
            viewModel.onShareClick()
        }

    }

    private fun PostBinding.render(post: Post) {
        authorName.text = post.author
        postContent.text = post.content
        datePost.text = post.published
        licksButton?.setImageResource(getLikeIconResId(post.likedByMy))
        licksQuantity.setText(post.numberLickes)
        shareQuantity.setText(post.numberShare)
    }

    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_licksred_24 else R.drawable.ic_lickes_24dp
}