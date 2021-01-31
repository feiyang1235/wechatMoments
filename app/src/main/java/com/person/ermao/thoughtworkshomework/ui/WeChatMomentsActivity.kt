package com.person.ermao.thoughtworkshomework.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.person.ermao.thoughtworkshomework.R
import com.person.ermao.thoughtworkshomework.adapter.TweetAdapter
import com.person.ermao.thoughtworkshomework.bean.BaseItem
import com.person.ermao.thoughtworkshomework.bean.Tweet
import com.person.ermao.thoughtworkshomework.databinding.ActivityWeChatMomentBinding
import com.person.ermao.thoughtworkshomework.loadImage
import com.person.ermao.thoughtworkshomework.viewmodel.TweetViewModel

class WeChatMomentsActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[TweetViewModel::class.java]
    }
    private var tweetList = mutableListOf<BaseItem>()
    private lateinit var binding: ActivityWeChatMomentBinding
    private lateinit var adapter: TweetAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_we_chat_moment)
        adapter = TweetAdapter(this, tweetList)
        binding.rvTweetList.adapter = adapter
        binding.rvTweetList.layoutManager = LinearLayoutManager(this)
        adapter.addRecyclerViewListener({
            val cacheList = viewModel.loadCacheTweetList()
            if (cacheList.isNullOrEmpty()) {
                return@addRecyclerViewListener
            }
            tweetList.addAll(cacheList)
            adapter.notifyDataSetChanged()
        }, binding.rvTweetList)
        viewModel.loadProfileInfo().observe(this, Observer {
            binding.ivProfileImage.loadImage(
                this@WeChatMomentsActivity,
                it.profileImage ?: "",
                true
            )
            binding.ivHeader.loadImage(
                this@WeChatMomentsActivity,
                it.avatar ?: "",
                false
            )
            binding.tvNickName.text = it.nick
        })
        viewModel.loadTweetListInfo().observe(this, Observer {
            viewModel.cacheTweetList.addAll(it.filterNotNull().filter { tweet ->
                !tweet.images.isNullOrEmpty() || tweet.content != null
            })
            val loadCacheTweetList = viewModel.loadCacheTweetList()
            tweetList.addAll(loadCacheTweetList)
            adapter.notifyDataSetChanged()
        })
    }
}