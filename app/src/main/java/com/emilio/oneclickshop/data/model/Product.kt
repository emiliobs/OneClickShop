package com.emilio.oneclickshop.data.model

import com.google.gson.annotations.SerializedName
import java.util.Locale

/***
 Represents a product from API
 Each field maps exactly to the JSON keys returned by /api/Products
 SerailizeName tells Gson to match JSON to this property
 */
data class Product(
        @SerializedName("id")
        val id: Int,

        @SerializedName("name")
        val name: String,

        @SerializedName("description")
        val description: String?,       // nullable — some products may have no description

        @SerializedName("imageURL")
        val imageURL: String,

        @SerializedName("price")
        val price: Double,

        @SerializedName("qty")
        val qty: Int,

        @SerializedName("categoryId")
        val categoryId: Int,

        @SerializedName("category")
        val category: Category?         // nullable — API sometimes includes it, sometimes not
)
{
    companion object
    {
        // Checks if the to (product) if safe to put on the shelf
        fun isValid(product : Product): Boolean
        {
            return try{
                product.name != null && product.price != null && product.price > 0.0
            }
            catch (ex: Exception){
                // If If anything goes wrong, we safely reject the product.
                false
            }
        }
    }

}