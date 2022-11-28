package fr.lotfirais.eatitup.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

class ImageDisplay {
    companion object {
        fun loadImageViaUrl(context: Context, view: ImageView, url: String) {
            val options: RequestOptions = RequestOptions()
                .centerInside()
                .transform(CircleCrop())

            Glide.with(context)
                .load(url)
                .apply(options)
                .into(view)
        }
    }
}