package com.example.inventoryapp

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.example.inventoryapp.database.Inventory
import com.example.inventoryapp.database.InventoryApplication
import kotlinx.android.synthetic.main.activity_editor.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class EditorActivity : AppCompatActivity() {


    var EXTRA = 1
    lateinit var inventory: Inventory
    private val inventoryViewModel: InventoryViewModel by viewModels {
        InventoryViewModel((application as InventoryApplication).repository)
    }


//    private lateinit var name: EditText
//    private lateinit var price: EditText
//    private lateinit var available: EditText
//    private lateinit var supplier: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        if (intent.hasExtra("edit")) {
            inventory = intent.extras?.get("edit") as Inventory
            name_edit_text.setText(inventory.name)
            price_edit_text.setText(inventory.price)
            available_edit_text.setText(inventory.available)
            supplier_edit_text.setText(inventory.supplier)
            if (inventory.image != null) {
                image_view.setImageBitmap(convertByteToBitmap(byteArrayOf(inventory.image!!)))
            }
            EXTRA = 0
        }
//        name = name_edit_text
//        price = price_edit_text
//        available = available_edit_text
//        supplier = supplier_edit_text
//
//        val saveButton = findViewById<Button>(R.id.saveItem)
//        saveButton.setOnClickListener {
//            val replyIntent = Intent()
//            if (TextUtils.isEmpty(name.text) || TextUtils.isEmpty(price.text)
//                || TextUtils.isEmpty(available.text) || TextUtils.isEmpty(supplier.text)) {
//                setResult(Activity.RESULT_CANCELED, replyIntent)
//            } else {
//                val inventory = Inventory(null, name.text.toString(), price.text.toString(),
//                    available.text.toString(), supplier.text.toString())
//                setResult(Activity.RESULT_OK, replyIntent)
//            }
//            finish()
//        }

    }

    private fun byteArrayOf(elements: String): ByteArray {
        return byteArrayOf(elements)
    }

    private fun convertByteToBitmap(byteArray: ByteArray): Bitmap? {
        val converted = ByteArrayInputStream(byteArray)
        return BitmapFactory.decodeStream(converted)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }

    fun save(item: MenuItem){


        when (item.itemId) {
            R.id.saveItem -> {
//                when {
//                    name_edit_text.text.trim().isBlank() -> {
//                        name_edit_text.error = "Fill the blank"
//                        return
//                    }
//
//                    price_edit_text.text.trim().isBlank() -> {
//                        price_edit_text.error = "Fill the blank"
//                        return
//                    }
//                    available_edit_text.text.trim().isBlank() -> {
//                        available_edit_text.error = "Fill the blank"
//                        return
//                    }
//                    supplier_edit_text.text.trim().isBlank() -> {
//                        supplier_edit_text.setText("No supplier")
//                        return
//                    }
//                    image_view.drawable == null -> {
//                        image_view.setImageResource(R.drawable.ic_launcher_background)
//                    }
//                }

                val inventoryName = name_edit_text.text.toString()
                val inventoryPrice = name_edit_text.text.toString()
                val inventoryAvailable = name_edit_text.text.toString()
                val inventorySupplier = name_edit_text.text.toString()

                val image: ByteArray = convert(image_view.drawable.toBitmap(100, 100, null))
                val inventory = Inventory(inventoryName, inventoryPrice, inventoryAvailable, inventorySupplier, image.toString())

                inventoryViewModel.insert(inventory)

                if (EXTRA == 1) {
                    inventoryViewModel.insert(inventory)
                } else if (EXTRA == 0) {
                    inventoryViewModel.update(inventory)
                }

                setResult(Activity.RESULT_OK, intent)
                finish()
             }

            R.id.deleteOneItem -> {
                inventoryViewModel.delete(inventory)
            }
        }
    }

    private fun convert(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, stream)
        return stream.toByteArray()
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}