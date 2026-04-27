package com.emilio.oneclickshop.data.repository

import com.emilio.oneclickshop.data.local.CartDao
import com.emilio.oneclickshop.data.model.CartItem
import kotlinx.coroutines.flow.Flow

/**
 * CartRepository is the single source of truth for cart data.
 * The ViewModel talks to this repository — never directly to the DAO.
 * This keeps our code clean and easy to test.
 *
 * Think of the DAO as the pen that writes in the libreta,
 * and the Repository as the person who decides WHAT to write.
 */
class CartRepository(private val cartDao: CartDao) {

    /**
     * Exposes all cart items as a Flow.
     * The Cart screen observes this — any change updates the UI instantly.
     */
    val allCartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()

    /**
     * Adds a product to the cart.
     * If the product is already in the cart, increases its quantity instead.
     * This prevents duplicate entries for the same product.
     */
    suspend fun addToCart(cartItem: CartItem) {
        val existing = cartDao.getCartItemByProductId(cartItem.productId)
        if (existing != null) {
            // Product already in cart — just increase the quantity
            cartDao.updateCartItem(existing.copy(quantity = existing.quantity + 1))
        } else {
            // New product — insert it fresh
            cartDao.insertCartItem(cartItem)
        }
    }

    /**
     * Updates an existing cart item (e.g. changing quantity).
     */
    suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem)
    }

    /**
     * Removes one item from the cart.
     */
    suspend fun removeFromCart(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    /**
     * Empties the entire cart.
     * Called after a successful checkout.
     */
    suspend fun clearCart() {
        cartDao.clearCart()
    }
}