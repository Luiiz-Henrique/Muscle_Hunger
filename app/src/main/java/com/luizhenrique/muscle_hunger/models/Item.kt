package com.luizhenrique.muscle_hunger.models

data class Item(
    val ingredients: String,
    val name: String,
    val photo: String,
    var price: Float,
    val time: Map<String, Int>,
    var quantity: Int = 0,
    val observations: String = "",
    var selected: Boolean = false,
)
