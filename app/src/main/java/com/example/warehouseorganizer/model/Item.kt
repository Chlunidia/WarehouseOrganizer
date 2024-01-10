package com.example.warehouseorganizer.model

data class Item(
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val rack: String = "",
    val quantity: Int = 0
)
