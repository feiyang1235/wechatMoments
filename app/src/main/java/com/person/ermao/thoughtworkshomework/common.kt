package com.person.ermao.thoughtworkshomework

import android.app.Activity
import android.content.res.Resources
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

//place和error 大小
fun loadImage(activity: Activity, imageUrl: String, imageView: ImageView, placeLarge: Boolean) {
    if (placeLarge) {
        Glide.with(activity)
            .load(imageUrl)
            .apply(getPlaceErrorCenter()).into(imageView)
    } else {
        Glide.with(activity)
            .load(imageUrl)
            .apply(getPlaceErrorCenterSmall())
            .into(imageView)
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
