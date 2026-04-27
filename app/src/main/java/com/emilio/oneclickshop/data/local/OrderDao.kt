package com.emilio.oneclickshop.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emilio.oneclickshop.data.model.Order
import com.emilio.oneclickshop.data.model.OrderItem
import kotlinx.coroutines.flow.Flow

/**
 * OrderDao = Data Access Object for orders and order items.
 * Handles saving completed orders and reading order history.
 */
@Dao
interface OrderDao {

    /**
     * Saves a new completed order to the database.
     * Returns the new order's auto-generated ID.
     * We need this ID to link the OrderItems to their parent Order.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    /**
     * Saves all the individual product lines for one order.
     * Each OrderItem knows which Order it belongs to via orderId.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)

    /**
     * Returns all orders, newest first (ORDER BY id DESC).
     * Flow = the Order History screen updates automatically.
     */
    @Query("SELECT * FROM orders ORDER BY id DESC")
    fun getAllOrders(): Flow<List<Order>>

    /**
     * Returns all items that belong to a specific order.
     * Used to show the product details inside an order.
     */
    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getOrderItems(orderId: Int): List<OrderItem>
}