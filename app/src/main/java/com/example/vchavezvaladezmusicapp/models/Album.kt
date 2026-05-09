package com.example.vchavezvaladezmusicapp.models

data class Album(
    val id: Int,
    val title: String,
    val artist: String,
    val image: String,
    val description: String? = null
)