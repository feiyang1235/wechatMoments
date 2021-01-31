package com.person.ermao.thoughtworkshomework.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.person.ermao.thoughtworkshomework.R
import com.person.ermao.thoughtworkshomework.bean.Tweet
import com.person.ermao.thoughtworkshomework.databinding.ActivityWeChatMomentBinding
import com.person.ermao.thoughtworkshomework.loadImage
import com.person.ermao.thoughtworkshomework.viewmodel.TweetViewModel

class WeChatMomentsActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[TweetViewModel::class.java]
    }
    private var tweetList = mutableListOf<Tweet>()
    private lateinit var binding: ActivityWeChatMomentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_we_chat_moment)
        viewModel.loadProfileInfo().observe(this, Observer {
            loadImage(
                this@WeChatMomentsActivity,
                it.profileImage ?: "",
                binding.ivProfileImage,
                true
            )
            loadImage(
                this@WeChatMomentsActivity,
                it.avatar ?: "",
                binding.ivHeader,
                false
            )
            binding.tvNickName.text = it.nick
        })
        viewModel.loadTweetListInfo().observe(this, Observer {
            viewModel.cacheTweetList.addAll(it.filterNotNull().filter { tweet ->
                tweet.comments == null && tweet.comments == null
            })
            val loadCacheTweetList = viewModel.loadCacheTweetList()
            tweetList.addAll(loadCacheTweetList)
        })
    }
}