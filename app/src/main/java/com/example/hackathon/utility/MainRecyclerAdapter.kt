package com.example.hackathon.utility

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.hackathon.databinding.ItemRankingBinding
import com.example.hackathon.gson.RankingListModelSealed
import com.example.hackathon.gson.TableDataSealed
import com.example.hackathon.gson.TableObject


class MainRecyclerAdapter(
    data: ArrayList<TableObject>
) : BaseQuickAdapter<TableObject, MainRecyclerAdapter.VH>(data) {

    override fun onCreateViewHolder(
        context: Context, parent: ViewGroup, viewType: Int
    ): VH {
        return VH(parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VH, position: Int, item: TableObject?
    ) {
        holder.binding.tvConversionRatePerUser.text = "ConversionRatePerUser: "+item?.conversion_rate_per_user.toString()
        holder.binding.tvConversionRateTotal.text ="ConversionRateTotal: "+ item?.conversion_rate_total.toString()
        holder.binding.tvPage.text ="Page: "+ item?.page.toString()
        holder.binding.tvPtype.text ="Ptype: "+ item?.ptype.toString()
        holder.binding.tvViewsPerUser.text ="ViewsPerUser: "+ item?.views_per_user.toString()
    }

    class VH(
        parent: ViewGroup,
        val binding: ItemRankingBinding = ItemRankingBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)


}
