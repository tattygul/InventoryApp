package com.example.inventoryapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Inventory::class], version = 1, exportSchema = false)
abstract class InventoryRoomDatabase : RoomDatabase() {

    abstract fun inventoryDao(): InventoryDao


    private class InventoryDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {

                    var inventoryDao = database.inventoryDao()

                    //Delete all content
                    inventoryDao.deleteAll()

                    val inventory = Inventory("name", "price", "availabe", "supplier")
                    inventoryDao.insert(inventory)
                }
            }
        }

    }

    companion object {

        @Volatile
        private var INSTANCE: InventoryRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): InventoryRoomDatabase {
            return (INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    InventoryRoomDatabase::class.java,
                    "inventory"
                )
                    .build()
            })
        }
    }

}