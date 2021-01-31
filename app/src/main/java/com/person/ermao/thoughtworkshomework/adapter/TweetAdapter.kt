package com.person.ermao.thoughtworkshomework.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.person.ermao.thoughtworkshomework.bean.*
import com.person.ermao.thoughtworkshomework.databinding.ItemCommentBinding
import com.person.ermao.thoughtworkshomework.databinding.ItemTweetBinding
import com.person.ermao.thoughtworkshomework.loadImage

class TweetAdapter(private val context: Context, private val itemList: List<BaseItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var lastVisibleItem: Int = 0
    private val uiHandler: Handler = Handler(Looper.getMainLooper())

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
                renderTweetUI(holder, baseItem)
            }
        } else if (holder is CommentItemViewHolder) {
            if (baseItem is CommentItemBean) {
                renderCommentUI(holder, baseItem)
            }
        }
    }

    //渲染评论视图
    private fun renderCommentUI(holder: CommentItemViewHolder, commentItemBean: CommentItemBean) {
        holder.getBinding().ivAvatar.loadImage(context, commentItemBean.avatar ?: "", false)
        holder.getBinding().tvCommentNickName.text = commentItemBean.nickName
        holder.getBinding().tvCommentContent.text = commentItemBean.content
    }

    //渲染推文视图
    private fun renderTweetUI(holder: TweetItemViewHolder, tweetItemBean: TweetItemBean) {
        holder.getBinding().tvNickName.text = tweetItemBean.nickName
        holder.getBinding().ivHeader.loadImage(context, tweetItemBean.avatar ?: "", false)
        holder.getBinding().tvContent.text = tweetItemBean.content
        val photoAdapter = PhotoGridAdapter(context, tweetItemBean.imageList)
        holder.getBinding().gvImage.adapter = photoAdapter
    }

    override fun getItemCount(): Int = itemList.size
    private var _loadDataFun: (() -> Unit)? = null
    fun addRecyclerViewListener(loadDataFun: () -> Unit, recyclerView: RecyclerView) {
        _loadDataFun = loadDataFun
        // 实现上拉加载重要步骤，设置滑动监听器，RecyclerView自带的ScrollListener
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastVisibleItem + 1 == itemCount) {
                        uiHandler.postDelayed({ loadDataFun() }, 500)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition();
            }
        })
    }
}