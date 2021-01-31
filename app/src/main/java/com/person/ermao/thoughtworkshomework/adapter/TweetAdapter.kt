package com.person.ermao.thoughtworkshomework.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.person.ermao.thoughtworkshomework.bean.*
import com.person.ermao.thoughtworkshomework.databinding.ItemCommentBinding
import com.person.ermao.thoughtworkshomework.databinding.ItemTweetBinding

class TweetAdapter(private val context: Context, private val itemList: List<BaseItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class TweetItemViewHolder(private val binding: ItemTweetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun getBinding() = binding
    }

    inner class CommentItemViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun getBinding() = binding
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TWEET_TYPE -> {
                TweetItemViewHolder(
                    ItemTweetBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                CommentItemViewHolder(
                    ItemCommentBinding.inflate(
                        LayoutInflater.from(context),
                        parent,
                        false
                    )
                )
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].itemType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val baseItem = itemList[position]
        if (holder is TweetItemViewHolder) {
            if (baseItem is TweetItemBean) {
                renderTweetUI(baseItem)
            }
        } else {
            if (baseItem is CommentItemBean) {
                renderCommentUI(baseItem)
            }
        }
    }

    private fun renderCommentUI(commentItemBean: CommentItemBean) {

    }

    private fun renderTweetUI(tweetItemBean: TweetItemBean) {

    }

    override fun getItemCount(): Int = itemList.size

}