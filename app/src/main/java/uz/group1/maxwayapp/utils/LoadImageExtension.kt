package uz.group1.maxwayapp.utils

import android.widget.ImageView
import uz.group1.maxwayapp.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String, onLoadingFinished: () -> Unit) {
    if (url.isNullOrEmpty()) {
        onLoadingFinished()
        return
    }

    val placeholders = listOf(
        R.drawable.placeholder_image_burger,
        R.drawable.placeholder_image_maxway_logo
    )

    Picasso.get()
        .load(url)
        .placeholder(placeholders.random())
        .error(R.drawable.ic_no_internet_connection)
        .fit()
        .centerCrop()
        .into(this, object : com.squareup.picasso.Callback {
            override fun onSuccess() {
                onLoadingFinished()
            }

            override fun onError(e: Exception?) {
                onLoadingFinished()
            }
        })
}