package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_layout.view.*

class ItemsAdapter(private val calculations: ArrayList<Calculation>) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = calculations.size

    override fun onBindViewHolder(holder: ItemsAdapter.ViewHolder, position: Int) {
        val calculation: Calculation = calculations[position]
        holder.date.text = calculation.date
        holder.weight.text = calculation.weight.toString()
        holder.height.text = calculation.height.toString()
        holder.bmi.text = calculation.bmi.toString()
        holder.weightUnit.text = calculation.weightUnit
        holder.heightUnit.text = calculation.heightUnit
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val date:TextView = itemView.date_TV
        val weight: TextView = itemView.weight_TV
        val height: TextView = itemView.height_TV
        val bmi: TextView = itemView.bmi_TV
        val weightUnit: TextView = itemView.weight_unit_TV
        val heightUnit: TextView = itemView.height_unit_TV
    }
}