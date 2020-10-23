package com.example.mobikodeassignment.model

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("id") val id: String,
    @SerializedName("publication_date") val publicationDate: String,
    @SerializedName("article_type") val articleType: String,
    @SerializedName("abstract") val abstract: List<String>
)