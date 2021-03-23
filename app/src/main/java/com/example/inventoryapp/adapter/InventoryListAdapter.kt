package com.example.inventoryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventoryapp.R
import com.example.inventoryapp.database.Inventory
import kotlinx.android.synthetic.main.item_list.view.*

class InventoryListAdapter(private var itemOnClickListener: ItemClickListener) :
    ListAdapter<Inventory, InventoryListAdapter.InventoriesViewHolder>(InventoryComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoriesViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return InventoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: InventoriesViewHolder, position: Int) {
        holder.bind(position)
    }

    fun getItemAt(position: Int): Inventory {
        return getItem(position)
    }

    inner class InventoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.product_image
        private val name: TextView = itemView.product_name_text_view
        private val price: TextView = itemView.product_price_text_view
        private val available: TextView = itemView.product_quantity_text_view
        private val supplier: TextView = itemView.product_supplier_text_view

        fun bind(position: Int) {
            var inventory = getItem(position)
            name.text = inventory.name
            price.text = inventory.price
            available.text = inventory.available.toString()
            supplier.text = inventory.supplier
            var img: String = inventory.image.toString()
            Glide.with(image.context)
                .load(img)
                .into(image)

            itemView.setOnClickListener {
                itemOnClickListener.onClick(position)
            }

        }
    }

    interface ItemClickListener {
        fun onClick(position: Int)
    }


    class InventoryComparator : DiffUtil.ItemCallback<Inventory>() {
        override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
            return oldItem.id == newItem.id
        }

    }


}