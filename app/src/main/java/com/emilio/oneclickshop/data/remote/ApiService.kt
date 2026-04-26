package com.emilio.oneclickshop.data.remote

import com.emilio.oneclickshop.data.model.Category
import com.emilio.oneclickshop.data.model.Product
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

/**
 * ApiService defines all the API endpoints we use in OneClickShop.
 * @GET means we are READING data from the server (not sending anything).
 * Retrofit reads these annotations and builds the actual HTTP requests for us.
 */
interface ApiService {

    /**
     * Fetches ALL products from the API.
     * Calls: GET https://oneclick.runasp.net/api/Products
     * Returns a list of Product objects.
     */
    @GET("api/Products")
    suspend fun getProducts(): List<Product>

    /**
     * Fetches a SINGLE product by its ID.
     * Calls: GET https://oneclick.runasp.net/api/Products/1
     * The {id} part is replaced with the actual product id at runtime.
     */
    @GET("api/Products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    /**
     * Fetches products filtered by category.
     * Calls: GET https://oneclick.runasp.net/api/Products/ByCategory/2
     */
    @GET("api/Products/ByCategory/{categoryId}")
    suspend fun getProductsByCategory(@Path("categoryId") categoryId: Int): List<Product>

    /**
     * Fetches ALL categories from the API.
     * Calls: GET https://oneclick.runasp.net/api/Categories
     */
    @GET("api/Categories")
    suspend fun getCategories(): List<Category>
}