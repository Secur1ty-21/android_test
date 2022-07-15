package com.sirius.test_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.sirius.test_app.DataModel
import com.sirius.test_app.R

class TagsAdapter(
    private val dataset: DataModel
) : RecyclerView.Adapter<TagsAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val materialButton: MaterialButton = view.findViewById(R.id.item_title)
    }

    /**
     * Создание новых views
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_items_tags, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Замена содержимого view
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.materialButton.text = dataset.tags[position]
    }

    /**
     * Возвращение размера dataset.tags
     */
    override fun getItemCount() = dataset.tags.size
}