package com.emilio.oneclickshop.data.repository

import android.util.Log
import com.emilio.oneclickshop.data.local.OrderDao
import com.emilio.oneclickshop.data.model.CartItem
import com.emilio.oneclickshop.data.model.Order
import com.emilio.oneclickshop.data.model.OrderItem
import kotlinx.coroutines.flow.Flow

/**
 * 📦 OrderRepository handles saving and reading orders locally.
 * When checkout is complete, this saves the order + all its items.
 */
class OrderRepository(private val orderDao: OrderDao) {

    /**
     * 🌊 Exposes all past orders as a Flow.
     * The Order History screen observes this automatically.
     */
    val allOrders: Flow<List<Order>> = orderDao.getAllOrders()

    /**
     * 🛒 Saves a complete order after checkout safely.
     * Steps:
     * 1. Insert the Order → get back the new orderId
     * 2. Convert each CartItem into an OrderItem linked to that orderId
     * 3. Insert all OrderItems
     *
     * @param order    The order with shipping details and total
     * @param cartItems The cart items being purchased
     */
    suspend fun placeOrder(order: Order, cartItems: List<CartItem>) {
        try
        {
            // Step 1: save the order and get its generated ID
            val orderId = orderDao.insertOrder(order)

            // Step 2: convert cart items to order items
            val orderItems = cartItems.map { cartItem ->
                OrderItem(
                    orderId = orderId.toInt(),
                    productId = cartItem.productId,
                    productName = cartItem.productName,
                    productPrice = cartItem.productPrice,
                    quantity = cartItem.quantity
                )
            }

            // Step 3: save all the order items
            orderDao.insertOrderItems(orderItems)
        }
        catch (e: Exception)
        {
            // 🛡️ Safely catch any database transaction errors
            Log.e("OrderRepository", "Error placing order: ${e.message}")
        }
    }

    /**
     * 🔍 Returns the individual items of one specific order safely.
     * Used to show product details in the Order History screen.
     */
    suspend fun getOrderItems(orderId: Int): List<OrderItem> {
        return try
        {
            orderDao.getOrderItems(orderId)
        }
        catch (e: Exception)
        {
            // 🛡️ Safely catch and return an empty list if the query fails
            Log.e("OrderRepository", "Error fetching order items: ${e.message}")
            emptyList()
        }
    }
}