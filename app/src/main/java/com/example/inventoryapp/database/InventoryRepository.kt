package com.example.inventoryapp.database

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData

class InventoryRepository(private val inventoryDao: InventoryDao) {

    val allInventories: LiveData<List<Inventory>> = inventoryDao.getAllData().asLiveData()

    fun insert(inventory: Inventory) {
        InsertInventory(inventoryDao).execute(inventory)
    }

    fun update(inventory: Inventory) {
        UpdateInventory(inventoryDao).execute(inventory)
    }

    fun delete(inventory: Inventory) {
        DeleteInventory(inventoryDao).execute(inventory)
    }

    fun deleteAll() {
        DeleteAllInventories(inventoryDao).execute()
    }


    class UpdateInventory(var inventoryDao: InventoryDao) : AsyncTask<Inventory, Void, Void>() {
        override fun doInBackground(vararg params: Inventory?): Void? {
            inventoryDao.insert(params[0]!!)
            return null
        }

    }

    class InsertInventory(var inventoryDao: InventoryDao) : AsyncTask<Inventory, Void, Void>() {
        override fun doInBackground(vararg params: Inventory?): Void? {
            inventoryDao.update(params[0]!!)
            return null
        }

    }

    class DeleteInventory(var inventoryDao: InventoryDao) : AsyncTask<Inventory, Void, Void>() {
        override fun doInBackground(vararg params: Inventory?): Void? {
            inventoryDao.deleteInventory(params[0]!!)
            return null
        }

    }

    class DeleteAllInventories(var inventoryDao: InventoryDao) :
        AsyncTask<Inventory, Void, Void>() {
        override fun doInBackground(vararg params: Inventory?): Void? {
            inventoryDao.deleteAll()
            return null
        }

    }

}
