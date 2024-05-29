package com.example.habittracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.databinding.StatsItemViewBinding
import com.example.habittracker.room.StatsRvInfo

class StatsRVAdapter(private val dataList: List<StatsRvInfo?>, private val listener: StatsItemClick) : RecyclerView.Adapter<StatsRVAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: StatsItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StatsItemViewBinding.inflate(
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
        holder.binding.tvTitle.text = data?.title
        holder.binding.tvDistance.text = data?.distance.toString()
        holder.binding.tvDate.text = data?.date.toString()
        holder.binding.tvTime.text = data?.time.toString()
        holder.binding.tvDay.text = data?.day.toString()
        holder.binding.root.setOnClickListener {
            listener.onUpdate(data!!)

        }
        holder.binding.btnDel.setOnClickListener {
            listener.onDelete(data!!.id)
        }
    }
}