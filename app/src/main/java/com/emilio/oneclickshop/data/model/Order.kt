package com.emilio.oneclickshop.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import okhttp3.Address

/**
 * Represent a completed order locally in Room.
 * Created when thne user completes the checkout process.
 *@Entity tells Room to create a table called "orders"
 * */
@Entity(tableName =  "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val customerName: String,
    val customerPhone: String,
    val shippingAddress: String,
    val city: String,
    val postcode: String,
    val totalAmount: Double,
    val orderDate: String, // Stored as text e.g. "5/04/2026"
    val status: String = "Pending"

)