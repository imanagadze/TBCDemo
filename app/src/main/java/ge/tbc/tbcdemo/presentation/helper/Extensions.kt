package ge.tbc.tbcdemo.presentation.helper

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadIcon(url: String) {
    Glide.with(context).load(url).into(this)
}