package com.example.inventoryapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "inventory")
data class Inventory(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "available") val available: String,
    @ColumnInfo(name = "supplier") val supplier: String,
    @ColumnInfo(name = "image") val image: String? = null,
) : Serializable {
    constructor(name: String, price: String, available: String, supplier: String)
            : this (name, price, available, supplier, image = null)

    constructor(inventory: Inventory) : this (
        name = inventory.name,
        price = inventory.price,
        available = inventory.available,
        supplier = inventory.supplier,
        image = inventory.image
            )

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0

}