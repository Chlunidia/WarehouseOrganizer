package com.example.warehouseorganizer.ui.add

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import com.example.warehouseorganizer.model.Item

data class AddUIState(
    val addEvent: AddEvent = AddEvent(),
)

data class AddEvent(
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val rack: String = "",
    val quantity: Int = 0,
    val imageBitmap:Bitmap? = null
)

fun AddEvent.toItem() = Item(
    id = id,
    imageUrl = imageUrl,
    name = name,
    rack = rack,
    quantity = quantity
)

data class DetailUIState(
    val addEvent: AddEvent = AddEvent(),
)

fun Item.toDetailItem(): AddEvent =
    AddEvent(
        id = id,
        imageUrl = imageUrl,
        name = name,
        rack = rack,
        quantity = quantity
    )

fun Item.toUIStateItem(): AddUIState = AddUIState(
    addEvent = this.toDetailItem()
)

data class HomeUIState(
    val listItems: List<Item> = listOf(),
    val dataLength: Int = 0
)