package com.example.inventoryapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventoryapp.adapter.InventoryListAdapter
import com.example.inventoryapp.database.InventoryApplication
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), InventoryListAdapter.ItemClickListener {

    companion object {

        var NEW = 1
        var EDIT = 0

    }

    private val adapter = InventoryListAdapter(this)
    private val inventoryViewModel: InventoryViewModel by viewModels {
        InventoryViewModel((application as InventoryApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fab.setOnClickListener() {
            val intent = Intent(this, EditorActivity::class.java)
            startActivityForResult(intent, NEW)
        }



        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)


        inventoryViewModel.allInventories.observe(this) { inventories ->
            inventories.let { adapter.submitList(it) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "SUCCESSFULLY ADDED!", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        } else if (requestCode == EDIT && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "SUCCESSFULLY UPDATED!", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onClick(position: Int) {
        var intent = Intent(baseContext, EditorActivity::class.java)
        inventoryViewModel.allInventories.observe(this, Observer {
            intent.apply {
                putExtra("edit", it[position])
            }
        })
        startActivityForResult(intent, EDIT)
    }

    fun deleteAll(item: MenuItem) {
        when (item.itemId) {


            R.id.deleteAll -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("All inventories would be deleted permanently")
                builder.setNegativeButton("NO") { dialogInterface, which ->
                    Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
                }
                builder.setPositiveButton("YES") { dialogInterface, which ->
                    inventoryViewModel.deleteAll()
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()

                }
                val alertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }

    }
}