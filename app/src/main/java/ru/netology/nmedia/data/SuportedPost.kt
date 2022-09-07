package ru.netology.nmedia.data

import android.view.View
import android.widget.ImageButton
import ru.netology.nmedia.databinding.ActivityMainBinding

class SuportedPost(private val cancel: View){
    var editedТow: Boolean = false

    fun visiblyCancelButton(){
        cancel.setVisibility(View.VISIBLE)
    }

    fun invisiblyCancelButton(){
        cancel.setVisibility(View.GONE)
    }
}
