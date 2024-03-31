package com.luizhenrique.muscle_hunger.models

data class Order(
    var name: String,
    val items: String,
    val price: Float,
    val hour: String,
)
