package fr.lotfirais.eatitup.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import fr.lotfirais.eatitup.R


object ImageDisplay {

    val thumbResultsOptions : RequestOptions = RequestOptions()
        .centerInside()
        .transform(CircleCrop())
        .override(250)

    val homeImageOptions : RequestOptions = RequestOptions()
        .centerInside()
        .transform(CircleCrop())

    val recipeImageOptions : RequestOptions = RequestOptions()
        .centerInside()

    fun loadImageViaUrl(context: Context, view: ImageView, url: String, options: RequestOptions) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.no_picture)
            .apply(options)
            .into(view)
    }
}