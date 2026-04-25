package com.emilio.oneclickshop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represent a single prodcut line within an order.
 * Each order can have multiple OrderItems
 * Linked to Order by orderId
 * */
@Entity(tableName = "order_items")
data class OrderItem(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val orderId: Int,               // links back to the parent Order
        val productId: Int,
        val productName: String,
        val productPrice: Double,
        val quantity: Int,
        val quantity12: Int
)