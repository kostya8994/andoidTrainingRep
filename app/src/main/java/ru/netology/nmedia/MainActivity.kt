package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.data.SuportedPost
import ru.netology.nmedia.data.adapter.PostsAdapter
import ru.netology.nmedia.data.util.hideKeyboard
import ru.netology.nmedia.data.util.showKeyboard
import ru.netology.nmedia.data.viewModel.PostViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val suportedPost = SuportedPost(binding.cancelEditButton)
        var textBeforeEdit = binding.contentEditText.text.toString()

        val adapter = PostsAdapter(
            viewModel::onLikeClick,
            viewModel::onShareClick,
            viewModel::onDeleteClick,
            viewModel::onEditClick,
            suportedPost
        )
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
                clearFocus()
                hideKeyboard()
                suportedPost.invisiblyCancelButton()
            }
        }
        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    requestFocus()
                    showKeyboard()
                } else {
                    clearFocus()
                    hideKeyboard()
                }
            }
        }

        binding.cancelEditButton.setOnClickListener {
            with(binding.contentEditText) {
                viewModel.currentPost.value = null
                viewModel.onSaveButtonClicked(textBeforeEdit)
                suportedPost.invisiblyCancelButton()
            }
        }
    }

}