package com.example.habittracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.RvItemViewBinding
import com.example.habittracker.room.RvInfo


class RVAdapter(val dataList: List<RvInfo?>, val listner: OnItemClick) : RecyclerView.Adapter<RVAdapter.viewHolder>() {
    inner class viewHolder(val binding: RvItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = RvItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val data = dataList[position]
        holder.binding.title.text = data?.title
        holder.binding.desc.text = data?.desc
        holder.binding.root.setOnClickListener {
            listner.onUpdate(data!!)

        }
    }
     fun getItemAtPosition(position: Int): RvInfo? {
        return dataList[position]
    }

}
