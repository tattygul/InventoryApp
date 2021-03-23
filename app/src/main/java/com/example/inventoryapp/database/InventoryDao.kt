package com.example.inventoryapp.database

import androidx.room.*
import com.example.inventoryapp.database.Inventory
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory order by id ASC")
    fun getAllData(): Flow<List<Inventory>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg inventory: Inventory)

    @Query("DELETE FROM inventory")
    fun deleteAll()

    @Delete
    fun deleteInventory(vararg inventory: Inventory)

    @Update
    fun update(vararg inventory: Inventory)
}