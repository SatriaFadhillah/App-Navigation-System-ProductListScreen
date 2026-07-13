package com.example.padilsholawat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShopViewModel : ViewModel() {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    private val _carItems = MutableStateFlow<List<CarItem>>(emptyList())
    val CarItem: StateFlow<List<CarItem>> = _carItems.asStateFlow()

    val product = listOf(
        Product(
            1,
            "Sajadah Emas",
            18000000,
            "Sangat halus dan dapat membuat nyaman ketika sholat dan rebahan",
            R.drawable.sajadah
        ),
        Product(
            2,
            "Tasbih Emas",
            23000000,
            "Sangat lembut setiap sentuhannya dan dapat membantu untuk flexing berangkat ke mesjid",
            R.drawable.tasbih
        ),
        Product(
            3,
            "Gamis Emas",
            40000000,
            "Sangat nyaman ketika di pakai di indoor maupun outdoor dan dapat membantu untuk flexing berangkat ke bukber",
            R.drawable.gamis
        ),
        Product(
            4,
            "Sorban Emas",
            63000000,
            "Sangat nyaman ketika di pakai di kepala dan dapat membantu untuk flexing berangkat ke bukber dan mesjid",
            R.drawable.sorban
        )
    )

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun addToCart(product: Product) {
        val currentCart = _carItems.value
        val existingItem = currentCart.find { it.product.id == product.id }

        _carItems.value = if (existingItem != null) {
            currentCart.map { item ->
                if (item.product.id == product.id) {
                    item.copy(quantity = item.quantity + 1)
                } else {
                    item
                }
            }
        } else {
            currentCart + CarItem(product, 1)
        }
    }

    fun removeFromCart(productId: Int) {
        _carItems.value = _carItems.value.filter { it.product.id != productId }
    }


    fun getTotalPrice(): Int {
        return _carItems.value.sumOf { it.product.price * it.quantity }
    }

    fun getCartItemCount(): Int {
        return _carItems.value.sumOf { it.quantity }
    }
}

