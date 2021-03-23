package com.example.inventoryapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventoryapp.database.Inventory
import com.example.inventoryapp.database.InventoryRepository

class InventoryViewModel(private val repository: InventoryRepository) : ViewModel(),
    ViewModelProvider.Factory {
    val allInventories: LiveData<List<Inventory>> = repository.allInventories

    fun insert(inventory: Inventory) {
        repository.insert(inventory)
    }


    fun update(inventory: Inventory) {
        repository.update(inventory)
    }


    fun delete(inventory: Inventory) {
        repository.delete(inventory)
    }


    fun deleteAll() {
        repository.deleteAll()
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InventoryViewModel(repository) as T
    }

}

