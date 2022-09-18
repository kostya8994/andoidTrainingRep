package ru.netology.nmedia

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding.edit.text?.toString())
        }

        val a = getIntent().getStringExtra("writtenPostContent")
        var b: String = ""
        if (a != null) b = a
        binding.edit.setText(b)
    }

    private fun onOkButtonClicked(postContent: String?){

        if(postContent.isNullOrBlank()){
            setResult(Activity.RESULT_CANCELED)
        } else {
            val resultIntent = Intent()
            resultIntent.putExtra(POST_CONTENT_EXTRA_KEY, postContent)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

    private companion object{
        const val POST_CONTENT_EXTRA_KEY = "postContent"
    }

    object ResultContract: ActivityResultContract<String?, String?>(){
        override fun createIntent(context: Context, input: String?) = Intent(context, NewPostActivity :: class.java).putExtra(
            "writtenPostContent", input)

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if(resultCode != Activity.RESULT_OK) return null
            val intent = intent?: return null

            return intent.getStringExtra(POST_CONTENT_EXTRA_KEY)
        }

    }
}