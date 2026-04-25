package com.emilio.oneclickshop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represent an item in the shopping cart.
 * THis is stored Locally in Room - not sent to the API.
 * @Entity tell Room to create a table called "carts_items"
 * */
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val productName: String,
    val productImageURL: String,
    val productPrice: Double,
    var quantity: Int = 1

)
{}