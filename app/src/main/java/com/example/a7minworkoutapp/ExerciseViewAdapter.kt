package com.example.a7minworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkoutapp.databinding.ItemExerciseStatusBinding

class ExerciseViewAdapter(val items :ArrayList<ExerciseModel>)
    : RecyclerView.Adapter<ExerciseViewAdapter.Viewholder>() {

        inner class Viewholder(binding: ItemExerciseStatusBinding) :RecyclerView.ViewHolder(binding.root){
            val tvItem =binding.tvItem
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val model: ExerciseModel = items[position]

        holder.tvItem.text = model.getId().toString()

        when {
            model.getIsSelected() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.item_circular_thin_color_accent_border
                    )
                holder.tvItem.setTextColor(Color.parseColor("#212121")) // Parse the color string, and return the corresponding color-int.
            }
            model.getIsCompleted() -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_accent_background)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else -> {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_gray_background)
                holder.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }

    }
}