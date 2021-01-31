package com.person.ermao.thoughtworkshomework.bean

data class TweetItemBean(override val itemType: Int = ITEM_TWEET_TYPE) : BaseItem() {
    var nickName: String? = null
    var avatar: String? = null
    var content: String? = null
    var imageList: List<String?>? = null
}