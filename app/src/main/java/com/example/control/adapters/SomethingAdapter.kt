package com.example.control.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.control.R
import com.example.control.databinding.ItemSomethingBinding
import com.example.control.listners.SomethingListener
import com.example.control.room.Something

class SomethingAdapter(private val somethings: List<Something>, var somethingListener: SomethingListener) :
    RecyclerView.Adapter<SomethingAdapter.SomethingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SomethingViewHolder {
        val itemSomethingBinding = ItemSomethingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SomethingViewHolder(itemSomethingBinding)
    }

    override fun onBindViewHolder(holder: SomethingViewHolder, position: Int) {
        holder.setSomethingData(somethings[position])
        holder.setAnimation(holder.itemView.context)
    }

    override fun getItemCount(): Int = somethings.size

    inner class SomethingViewHolder(var itemSomethingBinding: ItemSomethingBinding): RecyclerView.ViewHolder(itemSomethingBinding.root){
        fun setSomethingData(something: Something){
            itemSomethingBinding.tvTitle.text = something.title
            itemSomethingBinding.tvTime.text = something.time
            itemSomethingBinding.typeColor.setBackgroundColor(something.typeColor)
            itemSomethingBinding.tvTime.setOnClickListener{somethingListener.onTimeClicked(something)}
            itemSomethingBinding.root.setOnClickListener{somethingListener.onSomethingClicked(something)}
        }
        fun setAnimation(context: Context){
            itemSomethingBinding.root.animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_view)
        }
    }

    fun getSomethingByListId(id: Int): Something = somethings[id]
}