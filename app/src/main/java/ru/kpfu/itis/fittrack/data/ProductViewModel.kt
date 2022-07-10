package ru.kpfu.itis.fittrack.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kpfu.itis.fittrack.statsdata.EntireDatabase

class ProductViewModel(application: Application): AndroidViewModel(application) {
    val getAllProducts: LiveData<List<Product>>
    private val repository: ProductRepository

    init {
//        val productDao = ProductDatabase.getDatabase(application).productDao()
        val productDao = EntireDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        getAllProducts = repository.getAllProducts
    }

    fun addProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(product)
        }
    }

}
