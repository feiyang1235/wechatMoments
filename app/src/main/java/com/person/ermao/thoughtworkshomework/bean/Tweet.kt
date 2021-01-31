package com.person.ermao.thoughtworkshomework.bean

import java.io.Serializable

data class Tweet(
    var comments: List<Comment>? = null,
    var content: String? = null,
    var images: List<Image>? = null,
    var sender: SenderX? = null
) : Serializable

data class Comment(
    var content: String? = null,
    var sender: Sender? = null
) : Serializable

data class Image(
    var url: String? = null
) : Serializable

data class SenderX(
    var avatar: String? = null,
    var nick: String? = null,
    var username: String? = null
) : Serializable

data class Sender(
    var avatar: String? = null,
    var nick: String? = null,
    var username: String? = null
) : Serializable