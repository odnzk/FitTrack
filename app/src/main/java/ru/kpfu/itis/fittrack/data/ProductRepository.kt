package ru.kpfu.itis.fittrack.data

import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {
    val getAllProducts: LiveData<List<Product>> = productDao.getAllProducts()
    fun addProduct(product: Product) {
        productDao.add(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }
}