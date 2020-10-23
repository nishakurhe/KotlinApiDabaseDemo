package com.example.mobikodeassignment.model

import com.google.gson.annotations.SerializedName

class ResponseModel (
    @SerializedName("numFound") val numFound: Int,
    @SerializedName("start") val start: Int,
    @SerializedName("maxScore") val maxScore: Float,
    @SerializedName("docs") val docs: List<Book> ?= null
)