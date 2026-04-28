package com.emilio.oneclickshop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilio.oneclickshop.data.model.Category
import com.emilio.oneclickshop.data.model.Product
import com.emilio.oneclickshop.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ProductViewModel manages all product and category data for the UI.
 * The UI never talks to the repository directly — always through here.
 */
class ProductViewModel : ViewModel()
{

    private val repository = ProductRepository()

    // --- Products ---
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    // We keep the original list private, the UI will observe the filtered one!

    // --- Categories ---
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    // --- Selected product (for detail screen) ---
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    // --- Search query typed by the user ---
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // --- Loading state — true while waiting for the API ---
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // --- Error message — null if no error ---
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /**
     * 🔥 IMPROVEMENT: Reactive Filtered Products
     * Instead of a manual function, we use 'combine' to automatically create
     * a new StateFlow whenever the original products OR the search query change.
     * The UI will only observe THIS variable.
     */
    val filteredProducts: StateFlow<List<Product>> = combine(
        _products, _searchQuery
    ) { productList, query ->
        val cleanQuery = query.trim().lowercase()
        if (cleanQuery.isEmpty())
        {
            productList // If no search, return all products
        }
        else
        {
            productList.filter {
                it.name.lowercase().contains(cleanQuery) || it.description?.lowercase()
                    ?.contains(cleanQuery) == true
            }
        }
    }.stateIn(
        scope = viewModelScope, // Lives as long as the ViewModel
        started = SharingStarted.WhileSubscribed(5000), // Keeps flow active briefly during rotation
        initialValue = emptyList() // Starts empty
    )

    // Load products and categories automatically when ViewModel is created
    init
    {
        loadProducts()
        loadCategories()
    }

    /**
     * Fetches all products from the API.
     */
    fun loadProducts()
    {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            repository.getProducts().onSuccess { _products.value = it }.onFailure {
                    _errorMessage.value = "Could not load products. Check your connection."
                }
            _isLoading.value = false
        }
    }

    /**
     * Fetches all categories from the API.
     */
    fun loadCategories()
    {
        viewModelScope.launch {
            repository.getCategories().onSuccess { _categories.value = it }
                .onFailure { _errorMessage.value = "Could not load categories." }
        }
    }

    /**
     * Fetches products filtered by a specific category.
     * Called when the user selects a category on the Categories screen.
     */
    fun loadProductsByCategory(categoryId: Int)
    {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getProductsByCategory(categoryId).onSuccess { _products.value = it }
                .onFailure { _errorMessage.value = "Could not load products for this category." }
            _isLoading.value = false
        }
    }

    /**
     * Fetches a single product by its ID.
     * Called when the user taps on a product card.
     */
    fun loadProductById(id: Int)
    {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getProductById(id).onSuccess { _selectedProduct.value = it }
                .onFailure { _errorMessage.value = "Could not load product details." }
            _isLoading.value = false
        }
    }

    /**
     * Updates the search query as the user types.
     * Because 'filteredProducts' is observing this, the list updates automatically!
     */
    fun onSearchQueryChange(query: String)
    {
        _searchQuery.value = query
    }
}