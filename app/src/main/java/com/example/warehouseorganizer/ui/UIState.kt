package com.example.warehouseorganizer.ui.add

import android.net.Uri
import com.example.warehouseorganizer.model.Item

data class AddUIState(
    val addEvent: AddEvent = AddEvent(),
)

data class AddEvent(
    val imageUri: Uri = Uri.EMPTY,
    val name: String = "",
    val rack: String = "",
    val quantity: String = ""
)

fun AddEvent.toItem() = Item(
    name = name,
    rack = rack,
    quantity = quantity.toIntOrNull() ?: 0
)

data class DetailUIState(
    val addEvent: AddEvent = AddEvent(),
)

fun Item.toDetailItem(): AddEvent =
    AddEvent(
        name = name,
        rack = rack,
        quantity = quantity.toString()
    )

fun Item.toUIStateItem(): AddUIState = AddUIState(
    addEvent = this.toDetailItem()
)

data class HomeUIState(
    val listItems: List<Item> = listOf(),
    val dataLength: Int = 0
)
