package com.example.padilsholawat

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val description: String,
    val imageResId: Int
)

data class CarItem(
    val product: Product,
    val quantity: Int = 1
)