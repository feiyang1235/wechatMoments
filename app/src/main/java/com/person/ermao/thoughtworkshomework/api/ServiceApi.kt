package com.person.ermao.thoughtworkshomework.api

import com.person.ermao.thoughtworkshomework.bean.ProfileBean
import com.person.ermao.thoughtworkshomework.bean.Tweet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {
    /**
     *  获取简介信息
     */
    @GET("/user/jsmith")
    suspend fun getProfile(): Response<ProfileBean>

    /**
     *  获取Tweet列表
     */
    @GET("/user/jsmith/tweets")
    suspend fun getTweets(): Response<List<Tweet?>>
}