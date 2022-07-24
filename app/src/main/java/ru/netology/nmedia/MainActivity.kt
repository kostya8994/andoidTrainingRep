package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post)

        val likeButton = findViewById<ImageButton>(R.id.licksButton)
        val likeText = findViewById<TextView>(R.id.licksQuantity)
        val shareButton = findViewById<ImageButton>(R.id.shareButton)
        val shareText = findViewById<TextView>(R.id.shareQuantity)
        val post = Post(false, "999", "0")
        val postLogic = PostLogic(post)
        likeButton.setOnClickListener{
            if(!post.likedByMy)likeButton.setImageResource(R.drawable.ic_licksred_24) else likeButton.setImageResource(R.drawable.ic_lickes_24dp)
            if(!post.likedByMy) postLogic.clickLike() else postLogic.cancelingClickLike()
            likeText.setText(post.numberLickes)
            post.likedByMy = !post.likedByMy
        }
        shareButton.setOnClickListener{
            postLogic.clickShare()
            shareText.setText(post.numberShare)
        }
    }
}