package com.example.habittracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.RvItemViewBinding
import com.example.habittracker.room.RvInfo


class RVAdapter(private val dataList: List<RvInfo?>, private val listener: OnItemClick) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = dataList[position]
        holder.binding.title.text = data?.title
        holder.binding.desc.text = data?.desc
        holder.binding.root.setOnClickListener {
            listener.onUpdate(data!!)

        }
        holder.binding.btnDel.setOnClickListener {
            listener.onDelete(data!!.id)
        }
    }

//     fun getItemAtPosition(position: Int): RvInfo? {
//        return dataList[position]
//    }


}
