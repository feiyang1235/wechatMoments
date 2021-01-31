package com.person.ermao.thoughtworkshomework.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.person.ermao.thoughtworkshomework.R
import com.person.ermao.thoughtworkshomework.loadImage

/**
 * 加载九宫格图片的adapter
 * */
class PhotoGridAdapter(private val context: Context, private var photoUrlList: List<String?>?) :
    BaseAdapter() {
    override fun getCount(): Int = photoUrlList?.size ?: 0

    override fun getItem(position: Int): String = photoUrlList?.get(position) ?: ""

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val root = LayoutInflater.from(context).inflate(R.layout.item_gride_view, parent, false)
        val ivPhoto = root.findViewById<ImageView>(R.id.iv_photo)
        photoUrlList?.get(position)?.let { ivPhoto.loadImage(context, it, false) }
        return root
    }

}