@file:Suppress("DEPRECATION")

package com.sirius.test_app.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import com.sirius.test_app.DataModel
import com.sirius.test_app.R
import com.sirius.test_app.network.DownloadImageTask

class CommentsAdapter(
    private val dataset: DataModel
) : RecyclerView.Adapter<CommentsAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val materialTextViewUserName: MaterialTextView = view.findViewById(R.id.user_name_1)
        val materialTextViewDate: MaterialTextView = view.findViewById(R.id.date_comment_1)
        val materialTextViewComment: MaterialTextView = view.findViewById(R.id.comment_1)
        val floatingActionButton: FloatingActionButton = view.findViewById(R.id.user_image_1)
    }

    /**
     * Создание новых views
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Создание нового фрагмента
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_items_comments, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Замена содержимого view
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.materialTextViewUserName.text = dataset.reviews[position].userName
        holder.materialTextViewDate.text = dataset.reviews[position].date
        holder.materialTextViewComment.text = dataset.reviews[position].message
        try {
            DownloadImageTask(
                holder.floatingActionButton,
                true
            ).execute(dataset.reviews[position].userImage)
        } catch (e: Exception) {
            Log.e("loadImage", "$e")
        }
    }

    /**
     * Возвращение размера dataset.reviews
     */
    override fun getItemCount() = dataset.reviews.size
}