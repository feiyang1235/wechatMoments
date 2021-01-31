package com.person.ermao.thoughtworkshomework.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.person.ermao.thoughtworkshomework.App
import com.person.ermao.thoughtworkshomework.bean.ProfileBean
import com.person.ermao.thoughtworkshomework.bean.Tweet
import com.person.ermao.thoughtworkshomework.model.TweetModel

class TweetViewModel : ViewModel() {
    private val model by lazy {
        TweetModel()
    }
    private val profileLiveData = MutableLiveData<ProfileBean>()
    private val tweetLiveData = MutableLiveData<List<Tweet?>>()
    var cacheTweetList = mutableListOf<Tweet>()
    private var pageNum: Int = -1 //还没请求，默认为-1
    private val pageSize: Int = 5
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

    fun loadCacheTweetList():List<Tweet> {
        pageNum++
        val startIndex = pageNum * pageSize
        val endIndex = (pageNum + 1) * pageSize - 1
        //[startIndex,endIndex)左闭右开
        val resultList = mutableListOf<Tweet>()
        for (index in startIndex..endIndex) {
            resultList.add(cacheTweetList[index])
        }
        return resultList
    }
}