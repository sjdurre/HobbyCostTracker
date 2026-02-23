package com.example.hobbycosttracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hobbycosttracker.R
import com.example.hobbycosttracker.data.db.HobbySummary

class HobbyAdapter(
    private var items: List<HobbySummary>,
    private val onClick: (HobbySummary) -> Unit
) : RecyclerView.Adapter<HobbyAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.txtHobbyName)
        val spent: TextView = itemView.findViewById(R.id.txtSpent)
        val earned: TextView = itemView.findViewById(R.id.txtEarned)
        val net: TextView = itemView.findViewById(R.id.txtNet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_hobby, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val h = items[position]
        holder.name.text = h.name
        holder.spent.text = "Spent: $${"%.2f".format(h.totalCost)}"
        holder.earned.text = "Earned: $${"%.2f".format(h.totalRevenue)}"
        holder.net.text = "Net: $${"%.2f".format(h.net)}"

        holder.itemView.setOnClickListener { onClick(h) }
    }

    override fun getItemCount() = items.size

    fun update(newItems: List<HobbySummary>) {
        items = newItems
        notifyDataSetChanged()
    }
}