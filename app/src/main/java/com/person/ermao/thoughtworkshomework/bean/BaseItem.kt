package com.person.ermao.thoughtworkshomework.bean

const val ITEM_TWEET_TYPE = 0
const val ITEM_COMMENT_TYPE = 1

abstract class BaseItem {
    abstract val itemType: Int
}