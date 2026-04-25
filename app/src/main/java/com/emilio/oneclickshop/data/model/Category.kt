package com.emilio.oneclickshop.data.model

import com.google.gson.annotations.SerializedName

/**
 * Represents a prodcut category from API.
 * Maps to the JSON returned by /api/Categories
 */
data class Category(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,
)
{
   companion object
   {
       /**
        * Checks if the category has a valid name.
        * */
       fun isValid(category: Category): Boolean
       {
          return  try
          {
              category.name != null && category.name.isNotBlank()
          }
          catch (ex: Exception)
          {
              // Return false to prevent crashes
              false
          }
       }
   }
}