package uz.group1.maxwayapp.utils

import android.widget.ImageView
import uz.group1.maxwayapp.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) return

    val placeholders = listOf(
        R.drawable.placeholder_image_burger,
        R.drawable.placeholder_image_maxway_logo
    )

    val randomPlaceholder = placeholders.random()

    Picasso.get()
        .load(url)
        .placeholder(randomPlaceholder)
        .error(R.drawable.ic_no_internet_connection)
        .fit()
        .centerCrop()
        .into(this)
}