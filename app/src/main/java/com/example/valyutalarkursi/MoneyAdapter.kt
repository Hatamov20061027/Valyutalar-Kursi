package com.example.valyutalarkursi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.valyutalarkursi.databinding.ListItem1Binding

class MoneyAdapter(
    private val arrayList: ArrayList<Money>,
    val rvClickGroups: RVClickGroups,
) :
    RecyclerView.Adapter<MoneyAdapter.VH>() {

    inner class VH(private var itemRV: ListItem1Binding) : RecyclerView.ViewHolder(itemRV.root) {
        fun onBind(money: Money, position: Int) {
            itemRV.name.text = money.CcyNm_UZ.toString()
            itemRV.price.text = money.Rate.toString()
            itemRV.root.setOnClickListener {
                rvClickGroups.onClick(money)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ListItem1Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(arrayList[position], position)

    }

    override fun getItemCount(): Int = arrayList.size

    interface RVClickGroups {
        fun onClick(money: Money)
    }
}