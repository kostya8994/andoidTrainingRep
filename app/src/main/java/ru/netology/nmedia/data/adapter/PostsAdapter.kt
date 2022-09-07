package ru.netology.nmedia.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.SuportedPost
import ru.netology.nmedia.databinding.PostBinding
import kotlin.reflect.KFunction1

internal class PostsAdapter(
    private val onLikeClick: (Post) -> Unit,
    private val onShareClick: KFunction1<Post, Unit>,
    private val onRemoveClick: (Post) -> Unit,
    private val onEditClick: (Post) -> Unit,
    private val suportedPost: SuportedPost
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: PostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        fun bind(post: Post) {
            this.post = post
            with(binding) {
                authorName.text = post.author
                postContent.text = post.content
                datePost.text = post.published
                licksButton?.setImageResource(getLikeIconResId(post.likedByMy))
                licksQuantity.setText(post.numberLickes)
                shareQuantity.setText(post.numberShare)
                licksButton.setOnClickListener { onLikeClick(post) }
                shareButton.setOnClickListener { onShareClick(post) }
                options.setOnClickListener { popupMenu.show() }
            }
        }

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.option_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            onRemoveClick(post)
                            true
                        }
                        R.id.edit -> {
                            onEditClick(post)
                            suportedPost.visiblyCancelButton()
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_licksred_24 else R.drawable.ic_lickes_24dp
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
            newItem.id == oldItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
    }
}