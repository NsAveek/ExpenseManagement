package aveek.com.management.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import aveek.com.management.R
import com.bumptech.glide.Glide
import timber.log.Timber


/**
 * Binding adapter to bind styles runtime from xml
 * @param value binding adapter name
 * @return none
 */
@BindingAdapter("glideImageUrl")
fun loadImage(imageView: ImageView, url: String) {

    try {
        Glide
                .with(imageView.context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .into(imageView)

    } catch (e: IllegalArgumentException) {
        Timber.e(e)
    }

}