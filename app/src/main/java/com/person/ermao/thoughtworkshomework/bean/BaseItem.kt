package com.person.ermao.thoughtworkshomework.bean

const val ITEM_TWEET_TYPE = 0 //推条主条目类型
const val ITEM_COMMENT_TYPE = 1 //推条评论条目类型

abstract class BaseItem {
    abstract val itemType: Int  //列表item的type类型
}