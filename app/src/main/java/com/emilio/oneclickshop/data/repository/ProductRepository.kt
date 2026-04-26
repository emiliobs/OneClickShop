package com.emilio.oneclickshop.data.repository

import com.emilio.oneclickshop.data.model.Category
import com.emilio.oneclickshop.data.model.Product
import com.emilio.oneclickshop.data.remote.RetrofitInstance

/**
 * ProductRepository is the SINGLE SOURCE OF TRUTH for product data.
 * Think of it as the WAREHOUSE — the ViewModel asks the warehouse for products,
 * and the warehouse decides whether to get them from the API or local storage.
 *
 * In our case, products always come from the API (no local caching needed).
 * The ViewModel never talks to Retrofit directly — always through the Repository.
 * This keeps our code clean and easy to change later.
 */
class ProductRepository {

    /**
     * Fetches all products from the API.
     * 'suspend' means this runs in the background — it won't freeze the UI.
     * Returns a Result so the ViewModel can handle success and errors easily.
     */
    suspend fun getProducts(): Result<List<Product>> {
        return try {
            val products = RetrofitInstance.api.getProducts()
            Result.success(products)
        } catch (e: Exception) {
            // If something goes wrong (no internet, server down), we return the error
            Result.failure(e)
        }
    }

    /**
     * Fetches a single product by ID.
     * Used when the user taps on a product to see its details.
     */
    suspend fun getProductById(id: Int): Result<Product> {
        return try {
            val product = RetrofitInstance.api.getProductById(id)
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetches products filtered by a specific category.
     * Used when the user selects a category on the Categories screen.
     */
    suspend fun getProductsByCategory(categoryId: Int): Result<List<Product>> {
        return try {
            val products = RetrofitInstance.api.getProductsByCategory(categoryId)
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetches all categories from the API.
     * Used on the Home screen and Categories screen.
     */
    suspend fun getCategories(): Result<List<Category>> {
        return try {
            val categories = RetrofitInstance.api.getCategories()
            Result.success(categories)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}