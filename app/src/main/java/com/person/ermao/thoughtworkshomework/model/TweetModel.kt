package com.person.ermao.thoughtworkshomework.model

import com.person.ermao.thoughtworkshomework.api.RetrofitService
import com.person.ermao.thoughtworkshomework.api.ServiceApi

class TweetModel {
    suspend fun getProfile() = RetrofitService.createDefaultApi(ServiceApi::class.java).getProfile()


    suspend fun getTweets() = RetrofitService.createDefaultApi(ServiceApi::class.java).getTweets()

}