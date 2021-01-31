package com.person.ermao.thoughtworkshomework.bean

class CommentItemBean(override val itemType: Int = ITEM_COMMENT_TYPE) : BaseItem() {
    var nickName: String? = null
    var avatar: String? = null
    var content: String? = null
}