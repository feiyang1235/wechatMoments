package com.person.ermao.thoughtworkshomework

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

//place和error 大小
fun ImageView.loadImage(context: Context, imageUrl: String, placeLarge: Boolean) {
    if (placeLarge) {
        Glide.with(context)
            .load(imageUrl)
            .apply(getPlaceErrorCenter()).into(this)
    } else {
        Glide.with(context)
            .load(imageUrl)
            .apply(getPlaceErrorCenterSmall())
            .into(this)
    }
}

private fun getPlaceErrorCenter(): RequestOptions {
    return RequestOptions().placeholder(R.drawable.image_place).error(R.drawable.image_error)
        .centerCrop()
}

private fun getPlaceErrorCenterSmall(): RequestOptions {
    return RequestOptions().placeholder(R.drawable.image_place_small)
        .error(R.drawable.image_error_small).centerCrop()
}

inline val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
inline val Int.dp
    get() = this.toFloat().dp
