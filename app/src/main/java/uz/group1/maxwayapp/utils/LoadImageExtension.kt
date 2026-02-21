package uz.group1.maxwayapp.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import uz.group1.maxwayapp.R

fun ImageView.loadImage(url: String, onLoadingFinished: () -> Unit) {
    if (url.isNullOrEmpty()) {
        onLoadingFinished()
        return
    }

    val placeholders = listOf(
        R.drawable.placeholder_image_burger,
        R.drawable.placeholder_image_maxway_logo
    )

    Glide.with(this.context)
        .load(url)
        .placeholder(placeholders.random())
        .error(R.drawable.ic_no_internet_connection)
        .centerCrop()
        .listener(object : RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable?>,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable?>?,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }
        }).into(this)
}