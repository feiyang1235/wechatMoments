package com.person.ermao.thoughtworkshomework.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.person.ermao.thoughtworkshomework.App
import com.person.ermao.thoughtworkshomework.bean.*
import com.person.ermao.thoughtworkshomework.model.TweetModel

class TweetViewModel : ViewModel() {
    private val profileLiveData = MutableLiveData<ProfileBean>()
    private val tweetLiveData = MutableLiveData<List<Tweet?>>()
    var cacheTweetList = mutableListOf<Tweet>()
    private var pageNum: Int = -1 //还没请求，默认为-1
    private val pageSize: Int = 5
    private val model by lazy {
        TweetModel()
    }

    //加载简介信息
    fun loadProfileInfo() =
        liveData {
            try {
                val profileInfo = model.getProfile()
                val body = profileInfo.body()
                if (profileInfo.isSuccessful) {
                    emitSource(profileLiveData)
                    emit(body)
                }
            } catch (e: Exception) {
                Toast.makeText(App.getApp(), e.message, Toast.LENGTH_SHORT).show()
            }

        }

    //加载原始的推文列表信息
    fun loadTweetListInfo() =
        liveData {
            try {
                val tweetList = model.getTweets()
                val body = tweetList.body()
                if (tweetList.isSuccessful) {
                    emitSource(tweetLiveData)
                    emit(body)
                }
            } catch (e: Exception) {
                Toast.makeText(App.getApp(), e.message, Toast.LENGTH_SHORT).show()
            }

        }

    //加载内存缓存的推文列表信息
    fun loadCacheTweetList(): List<BaseItem> {
        pageNum++
        val startIndex = pageNum * pageSize
        val endIndex = (pageNum + 1) * pageSize - 1
        //[startIndex,endIndex]双闭区间
        if (startIndex >= cacheTweetList.size) {

            Toast.makeText(App.getApp(), "到底部啦！！", Toast.LENGTH_SHORT).show()
            return mutableListOf()
        }
        val originList = mutableListOf<Tweet>()
        for (index in startIndex..endIndex) {
            if (index >= cacheTweetList.size) {
                break
            }
            originList.add(cacheTweetList[index])
        }
        return machiningData(originList)
    }

    //原始推文列表信息转换成列表可使用的列表信息
    private fun machiningData(originList: MutableList<Tweet>): List<BaseItem> {
        val resultList = mutableListOf<BaseItem>()
        originList.forEach {
            resultList.add(TweetItemBean().apply {
                nickName = it.sender?.nick
                avatar = it.sender?.avatar
                content = it.content
                imageList = it.images?.map { image -> image.url }
            })
            it.comments?.forEach { comment ->
                resultList.add(CommentItemBean().apply {
                    nickName = comment.sender?.nick
                    avatar = comment.sender?.avatar
                    content = comment.content
                })
            }
        }
        return resultList
    }
}