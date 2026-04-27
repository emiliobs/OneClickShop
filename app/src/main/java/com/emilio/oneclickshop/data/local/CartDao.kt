package com.emilio.oneclickshop.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.emilio.oneclickshop.data.model.CartItem
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

/**
 * CartDao = Data Access Object for the shopping cart.
 * Think of this as the list of actions we can do with our cart "libreta":
 * add, update, remove, read, and clear.
 *
 * @Dao tells Room: "this interface defines database operations"
 */
@Dao
interface CartDao {

    /**
     * Adds a new item to the cart.
     * REPLACE = if the item already exists with same id, overwrite it.
     * Like crossing out the old line and writing a new one in the libreta.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    /**
     * Updates an existing item (e.g. changing quantity from 1 to 2).
     * Room matches by the item's primary key (id).
     */
    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    /**
     * Removes one specific item from the cart.
     * Like erasing one line from the libreta.
     */
    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    /**
     * Returns ALL items currently in the cart.
     * Flow = the UI updates AUTOMATICALLY when the cart changes.
     * No 'suspend' needed here because Flow handles it differently.
     * Like a live camera watching the libreta — any change shows instantly.
     */
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItem>>

    /**
     * Checks if a specific product is already in the cart.
     * Returns null if not found. Used to avoid duplicate entries.
     */
    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun getCartItemByProductId(productId: Int): CartItem?

    /**
     * Deletes ALL items from the cart at once.
     * Called after a successful checkout — the cart is now empty.
     */
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}