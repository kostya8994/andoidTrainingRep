package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.data.adapter.PostsAdapter
import ru.netology.nmedia.data.viewModel.PostViewModel
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel = PostViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(
            viewModel::onLikeClick,
            viewModel::onShareClick,
            viewModel::onDeleteClick,
            viewModel::onEditClick,
            viewModel::onOpenVideoClicked
        )
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        val activityLauncher = registerForActivityResult(
            NewPostActivity.ResultContract
        ) { postContent: String? ->
            postContent?.let(viewModel::onSaveButtonClicked)
        }

        binding.fab.setOnClickListener {
            activityLauncher.launch(null)
        }

        viewModel.currentPost.observe(this) { currentPost ->
            val content = currentPost?.content
            if (content != null) {
                activityLauncher.launch(content.toString())
            }

        }
        viewModel.currentPostVideo.observe(this) { currentPostVideo ->
            val videoUrl: String? = currentPostVideo?.video
            if (intent.resolveActivity(packageManager) != null) {
                if (videoUrl != null) {
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl.toString()))
                    startActivity(intent)
                }
            }
        }
    }

}