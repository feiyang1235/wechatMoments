package com.person.ermao.thoughtworkshomework.model

import com.person.ermao.thoughtworkshomework.api.RetrofitService
import com.person.ermao.thoughtworkshomework.api.ServiceApi
/**
 * 请求数据
 * */
class TweetModel {
    //请求简介信息
    suspend fun getProfile() = RetrofitService.createDefaultApi(ServiceApi::class.java).getProfile()

    //请求推条信息
    suspend fun getTweets() = RetrofitService.createDefaultApi(ServiceApi::class.java).getTweets()

}