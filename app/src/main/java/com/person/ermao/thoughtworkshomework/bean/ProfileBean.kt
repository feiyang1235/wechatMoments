package com.person.ermao.thoughtworkshomework.bean

import com.google.gson.annotations.SerializedName

data class ProfileBean(
    var avatar: String? = null,
    var nick: String? = null,
    @SerializedName("profile-image")
    var profileImage: String? = null,
    var username: String? = null
)