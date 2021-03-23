package com.example.inventoryapp.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class InventoryApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { InventoryRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { InventoryRepository(database.inventoryDao()) }


}