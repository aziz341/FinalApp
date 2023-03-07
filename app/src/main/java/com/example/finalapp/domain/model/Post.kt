package com.example.finalapp.domain.model

import com.example.finalapp.domain.model.Image
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Post(
    @SerializedName("post_title") var postTitle: String,
    @SerializedName("post_description") var post_description: String,
    @SerializedName("post_imge") var post_image: Image,
    @SerializedName("user_name") var user_name: String,
    @SerializedName("user_id") var user_id: String,
    @SerializedName("post_preptime") var post_preptime: String,
    @SerializedName("post_cooktime") var post_cooktime: String,
    @SerializedName("kkal") var kkal: String,
    @SerializedName("extendIngridients") var extendIngridients: String,
    @SerializedName("instruction") var instruction: String,
    @SerializedName("count_ingridients") var count_ingridients: String
    ): Serializable